嵌套集合模型


```


CREATE TABLE nodes (
        id INT AUTO_INCREMENT PRIMARY KEY,
        name VARCHAR(20) NOT NULL,
        lft INT NOT NULL,
        rgt INT NOT NULL
);

INSERT INTO nodes VALUES
  (1,'Vehicles',1,18),
  (2,'Motor Vehicles',2,11),
  (3,'Rail Vehicles',12,17),
  (4,'Cars',3,8),
  (5,'Buses',9,10),
  (6,'Trains',13,14),
  (7,'Trams',15,16),
  (8,'Convertibles',4,5),
  (9,'Minivans',6,7);

SELECT * FROM nodes ORDER BY id;



SELECT node.name
FROM nodes AS node,
        nodes AS parent
WHERE node.lft BETWEEN parent.lft AND parent.rgt
        AND parent.name = 'Vehicles'
ORDER BY node.lft;


SELECT name
FROM nodes
WHERE rgt = lft + 1;

SELECT parent.name
FROM nodes AS node,
        nodes AS parent
WHERE node.lft BETWEEN parent.lft AND parent.rgt
        AND node.name = 'FLASH'
ORDER BY node.lft;


SELECT node.name, (COUNT(parent.name) - 1) AS depth
FROM nodes AS node,
        nodes AS parent
WHERE node.lft BETWEEN parent.lft AND parent.rgt
GROUP BY node.name
ORDER BY node.lft;


SELECT CONCAT( REPEAT(' ', COUNT(parent.name) - 1), node.name) AS name
FROM nodes AS node,
        nodes AS parent
WHERE node.lft BETWEEN parent.lft AND parent.rgt
GROUP BY node.name
ORDER BY node.lft;


SELECT CONCAT( REPEAT('|----', COUNT(parent.name) - 1), node.name) AS name
FROM nodes AS node,
        nodes AS parent
WHERE node.lft BETWEEN parent.lft AND parent.rgt
GROUP BY node.name
ORDER BY node.lft;





SELECT node.name, (COUNT(parent.name) - (sub_tree.depth + 1)) AS depth
FROM nodes AS node,
        nodes AS parent,
        nodes AS sub_parent,
        (
                SELECT node.name, (COUNT(parent.name) - 1) AS depth
                FROM nodes AS node,
                nodes AS parent
                WHERE node.lft BETWEEN parent.lft AND parent.rgt
                AND node.name = 'PORTABLE ELECTRONICS'
                GROUP BY node.name
                ORDER BY node.lft
        )AS sub_tree
WHERE node.lft BETWEEN parent.lft AND parent.rgt
        AND node.lft BETWEEN sub_parent.lft AND sub_parent.rgt
        AND sub_parent.name = sub_tree.name
GROUP BY node.name
ORDER BY node.lft;








SELECT node.name, (COUNT(parent.name) - (sub_tree.depth + 1)) AS depth
FROM nodes AS node,
        nodes AS parent,
        nodes AS sub_parent,
        (
                SELECT node.name, (COUNT(parent.name) - 1) AS depth
                FROM nodes AS node,
                        nodes AS parent
                WHERE node.lft BETWEEN parent.lft AND parent.rgt
                        AND node.name = 'PORTABLE ELECTRONICS'
                GROUP BY node.name
                ORDER BY node.lft
        )AS sub_tree
WHERE node.lft BETWEEN parent.lft AND parent.rgt
        AND node.lft BETWEEN sub_parent.lft AND sub_parent.rgt
        AND sub_parent.name = sub_tree.name
GROUP BY node.name
HAVING depth <= 1
ORDER BY node.lft;







LOCK TABLE nodes WRITE;

SELECT @myRight := rgt FROM nodes
WHERE name = 'TELEVISIONS';

UPDATE nodes SET rgt = rgt + 2 WHERE rgt &gt; @myRight;
UPDATE nodes SET lft = lft + 2 WHERE lft &gt; @myRight;

INSERT INTO nodes(name, lft, rgt) VALUES('GAME CONSOLES', @myRight + 1, @myRight + 2);

UNLOCK TABLES






LOCK TABLE nodes WRITE;

SELECT @myLeft := lft FROM nodes

WHERE name = '2 WAY RADIOS';

UPDATE nodes SET rgt = rgt + 2 WHERE rgt &gt; @myLeft;
UPDATE nodes SET lft = lft + 2 WHERE lft &gt; @myLeft;

INSERT INTO nodes(name, lft, rgt) VALUES('FRS', @myLeft + 1, @myLeft + 2);

UNLOCK TABLES;








LOCK TABLE nodes WRITE;

SELECT @myLeft := lft, @myRight := rgt, @myWidth := rgt - lft + 1
FROM nodes
WHERE name = 'GAME CONSOLES';

DELETE FROM nodes WHERE lft BETWEEN @myLeft AND @myRight;

UPDATE nodes SET rgt = rgt - @myWidth WHERE rgt > @myRight;
UPDATE nodes SET lft = lft - @myWidth WHERE lft > @myRight;

UNLOCK TABLES;






LOCK TABLE nodes WRITE;

SELECT @myLeft := lft, @myRight := rgt, @myWidth := rgt - lft + 1
FROM nodes
WHERE name = 'PORTABLE ELECTRONICS';

DELETE FROM nodes WHERE lft = @myLeft;

UPDATE nodes SET rgt = rgt - 1, lft = lft - 1 WHERE lft BETWEEN @myLeft AND @myRight;
UPDATE nodes SET rgt = rgt - 2 WHERE rgt > @myRight;
UPDATE nodes SET lft = lft - 2 WHERE lft > @myRight;

UNLOCK TABLES;



```