  exports = (loginPayload) => {
    const { username, password } = loginPayload;

    let id = null;

    switch (username) {
      case "username":
        if (password == "password")
          id = "0"
        break;
    }

    if (!id) throw "Invalid credentials"

    return { "id": id, "name": username };
  };
