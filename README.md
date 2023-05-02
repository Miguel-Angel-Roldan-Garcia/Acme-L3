## ACME L3 PROJECT

Currently in sprint for milestone: **D04**

## Database creation script for current milestone
```

drop database if exists `Acme-L3-D04`;
create database `Acme-L3-D04`
	character set = 'utf8mb4'
  	collate = 'utf8mb4_unicode_ci';

grant select, insert, update, delete, create, drop, references, index, alter, 
        create temporary tables, lock tables, create view, create routine, 
        alter routine, execute, trigger, show view
    on `Acme-L3-D02`.* to 'acme-user'@'%';

drop database if exists `Acme-L3-D04-Test`;
create database `Acme-L3-D04-Test`
        character set = 'utf8mb4'
        collate = 'utf8mb4_unicode_ci';

grant select, insert, update, delete, create, drop, references, index, alter, 
        create temporary tables, lock tables, create view, create routine, 
        alter routine, execute, trigger, show view
    on `Acme-L3-D02-Test`.* to 'acme-user'@'%';
```
