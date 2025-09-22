CREATE TABLE exhibit_media (
    id BIGSERIAL PRIMARY KEY,
    exhibit_id BIGINT NOT NULL,
    media_type VARCHAR(50) NOT NULL,
    media_url VARCHAR(500) NOT NULL,
    title VARCHAR(255),
    description TEXT,
    display_order INTEGER DEFAULT 0,
    is_primary BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (exhibit_id) REFERENCES exhibits(id) ON DELETE CASCADE
);

CREATE INDEX idx_exhibit_media_exhibit_id ON exhibit_media(exhibit_id);
CREATE INDEX idx_exhibit_media_type ON exhibit_media(media_type);
CREATE INDEX idx_exhibit_media_primary ON exhibit_media(is_primary) WHERE is_primary = TRUE;
CREATE INDEX idx_exhibit_media_display_order ON exhibit_media(exhibit_id, display_order);

DELETE FROM visits WHERE exhibit_id IN (SELECT id FROM exhibits);
DELETE FROM comments WHERE exhibit_id IN (SELECT id FROM exhibits);
DELETE FROM exhibits;

ALTER SEQUENCE exhibits_id_seq RESTART WITH 1;

INSERT INTO exhibits (name, description, location, start_date, end_date, is_active, created_at, updated_at) VALUES

('Ancient Egyptian Treasures',
 'Journey through 3,000 years of Egyptian civilization. Discover mummies, golden artifacts, and the secrets of the pharaohs. This comprehensive collection features rare pieces from the tomb of Tutankhamun and interactive displays about daily life in ancient Egypt.',
 'East Wing, Floor 1, Gallery A',
 '2024-01-15 09:00:00',
 '2024-12-31 18:00:00',
 true,
 CURRENT_TIMESTAMP,
 CURRENT_TIMESTAMP),

('Leonardo da Vinci: Renaissance Master',
 'Explore the genius of Leonardo da Vinci through his masterpieces, inventions, and scientific studies. Features high-resolution reproductions of the Mona Lisa, The Last Supper, and his famous anatomical drawings alongside working models of his flying machines.',
 'West Wing, Floor 2, Gallery B',
 '2024-03-01 10:00:00',
 '2024-08-31 17:30:00',
 true,
 CURRENT_TIMESTAMP,
 CURRENT_TIMESTAMP),

('Dinosaurs: Giants of the Earth',
 'Meet the incredible creatures that ruled our planet millions of years ago. This family-friendly exhibit features life-sized animatronic dinosaurs, interactive dig sites, and the complete skeleton of a 65-foot-long Diplodocus.',
 'Central Hall, Floor 1',
 '2024-01-01 09:00:00',
 NULL,
 true,
 CURRENT_TIMESTAMP,
 CURRENT_TIMESTAMP),

('Modern Art Revolution: 1900-1950',
 'Experience the artistic movements that changed the world. From Picasso''s Cubism to Kandinsky''s abstract expressionism, this exhibit showcases how artists broke traditional boundaries and created entirely new ways of seeing.',
 'West Wing, Floor 3, Gallery C',
 '2024-02-14 11:00:00',
 '2024-09-15 16:00:00',
 true,
 CURRENT_TIMESTAMP,
 CURRENT_TIMESTAMP),

('Ocean Depths: Marine Life Explorer',
 'Dive into the mysterious world beneath the waves. This immersive exhibit features a walk-through tunnel aquarium, bioluminescent displays, and interactive touch tanks with sea creatures from coral reefs to the deepest ocean trenches.',
 'North Wing, Floor 1, Aquarium Hall',
 '2024-01-10 09:30:00',
 '2024-10-31 18:00:00',
 true,
 CURRENT_TIMESTAMP,
 CURRENT_TIMESTAMP),

('Space Exploration: Journey to the Stars',
 'Blast off on an incredible journey through our solar system and beyond. Features real NASA artifacts, a planetarium show, meteorite samples, and a full-scale replica of the International Space Station''s laboratory module.',
 'East Wing, Floor 3, Planetarium',
 '2024-04-12 10:00:00',
 '2025-01-31 17:00:00',
 true,
 CURRENT_TIMESTAMP,
 CURRENT_TIMESTAMP),

('Indigenous Cultures of the Americas',
 'Celebrate the rich heritage and traditions of Native American tribes from Alaska to Argentina. This respectfully curated exhibit showcases traditional crafts, storytelling, music, and the ongoing contributions of indigenous peoples to modern society.',
 'South Wing, Floor 2, Cultural Hall',
 '2024-05-01 09:00:00',
 '2024-11-30 17:30:00',
 true,
 CURRENT_TIMESTAMP,
 CURRENT_TIMESTAMP),

('Victorian Era: Life in the 1800s',
 'Step back in time to experience daily life during the Victorian era. Walk through recreated Victorian street scenes, explore a period home, and learn about the Industrial Revolution''s impact on society, fashion, and family life.',
 'East Wing, Floor 2, Period Rooms',
 '2024-06-15 10:30:00',
 '2025-03-15 16:30:00',
 true,
 CURRENT_TIMESTAMP,
 CURRENT_TIMESTAMP);

INSERT INTO exhibit_media (exhibit_id, media_type, media_url, title, description, display_order, is_primary) VALUES

(1, 'image', 'https://images.unsplash.com/photo-1539650116574-75c0c6d73200?w=800&h=600&fit=crop', 'Egyptian Sarcophagus', 'Golden sarcophagus from the tomb of Tutankhamun', 1, true),
(1, 'image', 'https://images.unsplash.com/photo-1578944032637-f09897c23a90?w=800&h=600&fit=crop', 'Hieroglyphics Wall', 'Ancient Egyptian hieroglyphics telling the story of the afterlife', 2, false),
(1, 'video', 'https://www.youtube.com/embed/example-egypt-video', 'Mummy Unwrapping Process', 'Documentary footage of archaeological mummy examination', 3, false),
(1, 'image', 'https://images.unsplash.com/photo-1596422426055-d71b58c1e5fc?w=800&h=600&fit=crop', 'Golden Artifacts', 'Collection of golden jewelry and ceremonial objects', 4, false),

(2, 'image', 'https://images.unsplash.com/photo-1541961017774-22349e4a1262?w=800&h=600&fit=crop', 'The Last Supper', 'High-resolution reproduction of Leonardo''s masterpiece', 1, true),
(2, 'image', 'https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=800&h=600&fit=crop', 'Flying Machine Model', 'Working replica of Leonardo''s ornithopter design', 2, false),
(2, '360_tour', 'https://virtualtour.example.com/leonardo-workshop', 'Leonardo''s Workshop', 'Interactive 360° tour of a recreated Renaissance workshop', 3, false),
(2, 'image', 'https://images.unsplash.com/photo-1594736797933-d0401ba51e67?w=800&h=600&fit=crop', 'Anatomical Drawings', 'Leonardo''s detailed studies of human anatomy', 4, false),

(3, 'image', 'https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=800&h=600&fit=crop', 'T-Rex Skeleton', 'Complete Tyrannosaurus Rex skeleton in attack pose', 1, true),
(3, 'video', 'https://www.youtube.com/embed/example-dino-video', 'Dinosaur Animation', 'CGI recreation of how dinosaurs moved and hunted', 2, false),
(3, 'image', 'https://images.unsplash.com/photo-1551993005-75c4131b6bd8?w=800&h=600&fit=crop', 'Fossil Dig Site', 'Interactive paleontology dig experience for children', 3, false),
(3, 'image', 'https://images.unsplash.com/photo-1596422426055-d71b58c1e5fc?w=800&h=600&fit=crop', 'Triceratops Display', 'Life-sized animatronic Triceratops with sound effects', 4, false),

(4, 'image', 'https://images.unsplash.com/photo-1578321272176-b7bbc0679853?w=800&h=600&fit=crop', 'Picasso Gallery', 'Collection of Picasso''s cubist masterpieces', 1, true),
(4, 'image', 'https://images.unsplash.com/photo-1541961017774-22349e4a1262?w=800&h=600&fit=crop', 'Abstract Expressionism', 'Kandinsky and other abstract expressionist works', 2, false),
(4, 'audio', 'https://audio.example.com/art-history-narration', 'Artist Commentary', 'Audio guide narrating the evolution of modern art', 3, false),

(5, 'image', 'https://images.unsplash.com/photo-1559827260-dc66d52bef19?w=800&h=600&fit=crop', 'Shark Tunnel', 'Walk-through tunnel with sharks and rays swimming overhead', 1, true),
(5, 'video', 'https://www.youtube.com/embed/example-ocean-video', 'Deep Sea Exploration', 'Footage from deep sea research submarines', 2, false),
(5, 'image', 'https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=800&h=600&fit=crop', 'Coral Reef Display', 'Vibrant coral reef ecosystem with tropical fish', 3, false),
(5, 'image', 'https://images.unsplash.com/photo-1583212292454-1fe6229603b7?w=800&h=600&fit=crop', 'Touch Tank Experience', 'Interactive area where visitors can touch sea stars and sea urchins', 4, false),

(6, 'image', 'https://images.unsplash.com/photo-1446776877081-d282a0f896e2?w=800&h=600&fit=crop', 'ISS Module Replica', 'Full-scale replica of International Space Station laboratory', 1, true),
(6, 'image', 'https://images.unsplash.com/photo-1502134249126-9f3755a50d78?w=800&h=600&fit=crop', 'Moon Rock Display', 'Authentic lunar samples from Apollo missions', 2, false),
(6, 'video', 'https://www.youtube.com/embed/example-space-video', 'Mars Rover Mission', 'Documentary about recent Mars exploration missions', 3, false),
(6, '360_tour', 'https://virtualtour.example.com/space-station', 'Virtual Space Walk', 'Experience a spacewalk outside the International Space Station', 4, false),

(7, 'image', 'https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=800&h=600&fit=crop', 'Traditional Crafts', 'Handwoven textiles and pottery from various tribes', 1, true),
(7, 'audio', 'https://audio.example.com/indigenous-stories', 'Storytelling Circle', 'Traditional stories told by tribal elders', 2, false),
(7, 'image', 'https://images.unsplash.com/photo-1551993005-75c4131b6bd8?w=800&h=600&fit=crop', 'Ceremonial Regalia', 'Traditional ceremonial clothing and sacred objects', 3, false),

(8, 'image', 'https://images.unsplash.com/photo-1513475382585-d06e58bcb0e0?w=800&h=600&fit=crop', 'Victorian Street Scene', 'Recreated London street from the 1880s', 1, true),
(8, 'image', 'https://images.unsplash.com/photo-1578944032637-f09897c23a90?w=800&h=600&fit=crop', 'Period Home Interior', 'Authentic Victorian parlor and kitchen displays', 2, false),
(8, 'audio', 'https://audio.example.com/victorian-music', 'Period Music', 'Popular songs and classical music from the Victorian era', 3, false),
(8, 'image', 'https://images.unsplash.com/photo-1594736797933-d0401ba51e67?w=800&h=600&fit=crop', 'Industrial Revolution Display', 'Working models of Victorian-era machinery', 4, false);
