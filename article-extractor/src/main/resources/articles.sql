#PostgreSQL

drop table Articles;

create table Articles (
   id serial not null,
   news_outlet varchar(100) not null,
   title varchar(100) not null,
   section varchar(100) not null,
   content text not null,
   author varchar(100),
   place varchar(100),
   article_url varchar not null,
   img_url varchar,
   date_published date,
   time_extracted date,
   created_at timestamp,
   updated_at timestamp,
   Constraint PK_Articles PRIMARY KEY (news_outlet,title,section)
);