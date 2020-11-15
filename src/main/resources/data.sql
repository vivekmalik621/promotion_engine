insert into Product(ID,SKU,UNIT_PRICE) values (1L,'A', 50);
insert into Product(ID,SKU,UNIT_PRICE) values (2L,'B', 30);
insert into Product(ID,SKU,UNIT_PRICE) values (3L,'C', 20);
insert into Product(ID,SKU,UNIT_PRICE) values (4L,'D', 15);
insert into Product(ID,SKU,UNIT_PRICE) values (5L,'E', 5);

insert into Promotion(ID,COMBO_REFERENCE_ID,PRICE,QUANTITY,TYPE,SKU_MAPPING) values (1L,0L,130,3,'SINGLE',1L);
insert into Promotion(ID,COMBO_REFERENCE_ID,PRICE,QUANTITY,TYPE,SKU_MAPPING) values (2L,0L,45,2,'SINGLE',2L);
insert into Promotion(ID,COMBO_REFERENCE_ID,PRICE,QUANTITY,TYPE,SKU_MAPPING) values (3L,1L,30,1,'COMBO',3L);
insert into Promotion(ID,COMBO_REFERENCE_ID,PRICE,QUANTITY,TYPE,SKU_MAPPING) values (4L,1L,30,1,'COMBO',4L);
insert into Promotion(ID,COMBO_REFERENCE_ID,PRICE,QUANTITY,TYPE,SKU_MAPPING) values (5L,1L,30,1,'COMBO',5L);


