INSERT INTO users (username, password, email, role, points) VALUES ('admin', '$2a$10$r.7gB4aZ2y0mPj3eH2S3V.9u/WwM2z4LwI4w/9H6Wq.5Q7r.w.e.G', 'admin@ecotrack.ai', 'ROLE_ADMIN', 100);
INSERT INTO users (username, password, email, role, points) VALUES ('john', '$2a$10$r.7gB4aZ2y0mPj3eH2S3V.9u/WwM2z4LwI4w/9H6Wq.5Q7r.w.e.G', 'john@example.com', 'ROLE_USER', 50);

-- Note: The password above is 'password' encoded with BCrypt.
