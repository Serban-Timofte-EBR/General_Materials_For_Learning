# How to Build a CRUD REST API with NestJS, Docker, Swagger, and Prisma

## How to Set Up the NestJS Project

Let's begin by installing the NestJS CLI on your system:
    
```bash
npm i -g @nestjs/cli
```

To spin up a new project, execute the following command:

```bash
nest new recipe
```

To start your project and see the 'Hello World!' message in action, execute the following command:

```bash
npm run start:dev
```

## How to Create a PostgreSQL Instance with Docker

To create a PostgreSQL instance, execute the following command:

```bash
touch docker-compose.yml
```

Here's a quick breakdown of this configuration:

- image: postgres:13.5: Specifies the Docker image for the PostgreSQL database.

- restart: always: Ensures the container restarts if it stops.

- environment: Sets the username and password for the database.

- volumes: Mounts a volume to persist database data, even if the container is stopped or removed.

- ports: Exposes port 5432 on both the host machine and the container for database access.

To fire up the PostgreSQL container, execute the following command in your project's root directory (and also make sure you have open the docker desktop app and it is running):
    
```bash
docker-compose up
```

_Note_: if you close the terminal window, it will also stop the container. To prevent this, you can run the container in detached mode. This mode allows the container to run indefinitely in the background.
    
```bash
docker-compose up -d
```

To stop the container, use the following command:

```bash
docker-compose down
```

## How to Set Up Prisma

To install Prisma, execute the following command:

```bash
npm install prisma -D
```

To initialize Prisma, execute the following command:

```bash
npx prisma init
```

This will create a new prisma directory with a schema.prisma file. This is the main configuration file that contains your database schema. This command also creates a .env file inside your project.

## How to Set Your Environment Variable

The .env file contains the environment variables required to connect to your database. Open this file and replace the contents with the following:

```.env
DATABASE_URL="postgres://recipe:RecipePassword@localhost:5444/recipe"
```

Note: If you changed the port number in your docker-compose.yml file, ensure you update the port number in the DATABASE_URL environment variable with the port number you used.

## How to Model the Data

Open the schema.prisma file and add the following code:

```prisma
model Recipe {
  id           Int      @id @default(autoincrement())
  title        String   @unique
  description  String?
  ...
}
```

## How to Migrate the Database

Now that we've defined our database schema, we're ready to migrate our database. This will create the database tables and fields defined in our schema.prisma file.

To migrate your database, execute the following command:

```bash
npx prisma migrate dev --name init
```

## How to Seed the Database

Firstly, create a seed file called prisma/seed.ts. This file will contain the dummy data and queries needed to seed your database.

```bash
touch prisma/seed.ts
```

Now before we can seed our database, we need add a script to our package.json file. Open the package.json file and add the following script:

```json
// package.json

// ...
"scripts": {
// ...
},
"dependencies": {
// ...
},
"devDependencies": {
// ...
},
"jest": {
// ...
},
// pasting the prisma script here
"prisma": {
"seed": "ts-node prisma/seed.ts"
}
```

Run this command to seed your database:

```bash
npx prisma db seed
```

## How to Create a Prisma Service
The Nest CLI gives you an easy way to generate modules and services directly from the CLI. Run the following command in your terminal:

```bash
npx nest generate module prisma
npx nest generate service prisma
```

So you should see something like this:

```bash
src/prisma/prisma.service.spec.ts
src/prisma/prisma.service.ts
src/prisma/prisma.module.ts
```

