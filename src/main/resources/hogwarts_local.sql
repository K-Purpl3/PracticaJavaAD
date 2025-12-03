DROP TABLE IF EXISTS asignatura CASCADE;
DROP TABLE IF EXISTS profesor CASCADE;
DROP TABLE IF EXISTS mascota CASCADE;
DROP TABLE IF EXISTS casa CASCADE;
DROP TABLE IF EXISTS estudiante CASCADE;
DROP TABLE IF EXISTS estudiante_asignatura CASCADE;

CREATE TABLE casa (
                      id_casa SERIAL PRIMARY KEY,
                      nombre VARCHAR(50) NOT NULL
);

CREATE TABLE mascota (
                         id_mascota SERIAL PRIMARY KEY,
                         nombre VARCHAR(50),
                         tipo VARCHAR(50)
);

CREATE TABLE estudiante (
                            id_estudiante SERIAL PRIMARY KEY,
                            nombre VARCHAR(50),
                            apellido VARCHAR(50),
                            fecha_nacimiento DATE,
                            curso INT,
                            id_casa INT REFERENCES casa(id_casa),
                            id_mascota INT REFERENCES mascota(id_mascota)
);

CREATE TABLE profesor (
                          id_profesor SERIAL PRIMARY KEY,
                          nombre VARCHAR(50),
                          apellido VARCHAR(50)
);

CREATE TABLE asignatura (
                            id_asignatura SERIAL PRIMARY KEY,
                            nombre VARCHAR(150),
                            aula VARCHAR(20),
                            id_profesor INT REFERENCES profesor(id_profesor)
);

CREATE TABLE estudiante_asignatura (
                                       id SERIAL PRIMARY KEY,
                                       id_estudiante INT REFERENCES estudiante(id_estudiante),
                                       id_asignatura INT REFERENCES asignatura(id_asignatura)
);
