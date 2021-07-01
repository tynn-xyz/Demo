package demo.img.color

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log.w
import android.widget.ImageView
import android.widget.TextView
import androidx.palette.graphics.Palette
import androidx.palette.graphics.Palette.from
import com.squareup.picasso.Callback
import com.squareup.picasso.RequestCreator
import demo.img.R.id.palette_target
import java.lang.ref.WeakReference
import java.util.*

internal class PaletteTarget private constructor(
    imageView: ImageView,
    textView: TextView,
) {

    private val imageView = WeakReference(imageView)
    private val textView = WeakReference(textView)

    private val bitmap get() = (imageView.get()?.drawable as? BitmapDrawable)?.bitmap

    private val callback = object : Callback {

        override fun onSuccess() {
            val bitmap = bitmap ?: return
            cache[bitmap]?.useColors() ?: from(bitmap)
                .resizeBitmapArea(512)
                .setRegion(bitmap.width / 2, bitmap.height * 7 / 8, bitmap.width, bitmap.height)
                .generate { cache[bitmap] = it?.useColors() }
        }

        override fun onError(e: Exception) {
            w("PaletteTarget", e)
        }
    }

    private fun Palette.useColors() = apply {
        val swatch = dominantSwatch ?: return@apply
        imageView.get()?.setBackgroundColor(swatch.rgb)
        textView.get()?.setTextColor(swatch.bodyTextColor)
    }

    companion object {

        private val cache = WeakHashMap<Bitmap, Palette>()

        private fun TextView.getTarget(imageView: ImageView) =
            (getTag(palette_target) as? PaletteTarget)?.takeIf {
                it.imageView.get() == imageView
            } ?: PaletteTarget(imageView, this).also {
                setTag(palette_target, it)
            }

        fun RequestCreator.into(
            imageView: ImageView,
            textView: TextView,
        ) = with(textView.getTarget(imageView)) {
            into(imageView, callback)
        }
    }
}
