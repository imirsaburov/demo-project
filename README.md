Instruction to launching the project

If you want to use you own db, you need configure environment variables

`DB_HOST`
`DB_PORT`
`DB_USERNAME`
`DB_PASSWORD`
`DB_NAME`

And execute bellow command

```shell
docker compose up -d
```

If you do not have own db, just execute bellow command

```shell
docker compose -f compose-standalone.yml up -d
```

There is liquibase migrations which automatically execute sql ddl commands.


users table ddl command
```postgresql
create table public.users
(
    id               bigint generated by default as identity constraint pk_users primary key,
    created_at       timestamp    not null,
    last_modified_at timestamp    not null,
    firstname        varchar(255) not null,
    lastname         varchar(255) not null,
    balance          numeric      not null
);
```

transactions table ddl command
```postgresql
create table public.transactions
(
    id               bigint generated by default as identity constraint pk_transactions primary key,
    created_at       timestamp not null,
    last_modified_at timestamp not null,
    time             timestamp not null,
    amount           numeric   not null,
    type             smallint  not null,
    user_id          bigint    not null constraint fk_transactions_user_id_on_user_id references public.users
);
```

