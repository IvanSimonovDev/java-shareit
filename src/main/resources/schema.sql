CREATE TABLE IF NOT EXISTS users (
    id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name text,
    email text
);

CREATE TABLE IF NOT EXISTS items (
    id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    owner_id integer REFERENCES users (id),
    name text NOT NULL,
    description text,
    available boolean NOT NULL
);