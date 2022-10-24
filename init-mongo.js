db.createUser(
    {
        user: "drones",
        pwd: "drones123",
        roles: [
            {
                role: "readWrite",
                db: "drones"
            }
        ]
    }
)