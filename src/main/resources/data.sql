delete from ingredient_ref;
delete from taco;
delete from taco_order;
delete from ingredient;

insert into ingredient (id, name, type) values
    ('FLTO', 'Flour Tortilla', 'WRAP'),
    ('COTO', 'Corn Tortilla', 'WRAP'),
    ('GRBF', 'Ground Beef', 'PROTEIN'),
    ('CARN', 'Carnitas', 'PROTEIN'),
    ('TMTO', 'Diced Tomatoes', 'VEGGIES'),
    ('LETC', 'Lettuce', 'VEGGIES'),
    ('CHED', 'Cheddar', 'CHEESE'),
    ('JACK', 'Monterrey Jack', 'CHEESE'),
    ('SLSA', 'Salsa', 'SAUCE'),
    ('SRCR', 'Sour Cream', 'SAUCE');