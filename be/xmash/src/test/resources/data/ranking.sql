INSERT INTO app_user (app_user_id, user_id, email, name, password, gender) VALUES
               (1, 'user1', 'user1@example.com', 'user1', 'password1', 'MALE'),
               (2, 'user2', 'user2@example.com', 'user2', 'password2', 'MALE'),
               (3, 'user3', 'user3@example.com', 'user3', 'password3', 'MALE'),
               (4, 'user4', 'user4@example.com', 'user4', 'password4', 'MALE'),
               (5, 'user5', 'user5@example.com', 'user5', 'password5', 'MALE');

INSERT INTO user_ranking (season, tier, lp, team_tier, team_lp, app_user_id) VALUES
                 (1, 'SILVER', 1000, 'SILVER', 1400, 1),
                 (1, 'SILVER', 1100, 'SILVER', 1300, 2),
                 (1, 'SILVER', 1200, 'SILVER', 1200, 3),
                 (1, 'SILVER', 1300, 'SILVER', 1100, 4),
                 (1, 'SILVER', 1400, 'SILVER', 1000, 5);