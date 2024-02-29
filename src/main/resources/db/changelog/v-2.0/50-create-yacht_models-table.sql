CREATE TABLE yacht_models (
                              id BIGSERIAL NOT NULL,
                              make VARCHAR(40) NOT NULL,
                              model VARCHAR(40) NOT NULL,
                              year INTEGER NOT NULL,
                              length_overall DECIMAL(5, 2) NOT NULL,
                              beam_width DECIMAL(5, 2) NOT NULL,
                              draft_depth DECIMAL(5, 2) NOT NULL,
                              keel_type_id BIGINT,
                              fuel_type_id BIGINT,
                              created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                              updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NULL,
                              PRIMARY KEY (id),
                              UNIQUE (make, model, year),
                              CONSTRAINT fk_yacht_models_keel_types FOREIGN KEY (keel_type_id) REFERENCES keel_types (id),
                              CONSTRAINT fk_yacht_models_fuel_types FOREIGN KEY (fuel_type_id) REFERENCES fuel_types (id)
);
