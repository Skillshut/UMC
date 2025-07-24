CREATE TABLE venue_event (
                             event_id BIGINT NOT NULL,
                             brand TEXT NOT NULL,
                             provider TEXT NOT NULL,
                             external_id TEXT NOT NULL,
                             PRIMARY KEY (event_id, brand, provider, external_id),
                             FOREIGN KEY (event_id) REFERENCES event(id) ON DELETE CASCADE,
                             FOREIGN KEY (brand, provider, external_id)
                                 REFERENCES venue(brand, provider, external_id) ON DELETE CASCADE
);