
# Steps to Create a CRUD Application in Nest.js

This guide explains how to create a CRUD (Create, Read, Update, Delete) application in **Nest.js** using **Prisma** as the ORM.

---

## Step 1: Install Nest.js CLI and Create a Project

1. Install the Nest.js CLI globally:
   ```bash
   npm install -g @nestjs/cli
   ```

2. Create a new project:
   ```bash
   nest new project-name
   ```

3. Navigate into the project directory:
   ```bash
   cd project-name
   ```

---

## Step 2: Install Prisma and Set It Up

1. Install Prisma and its client:
   ```bash
   npm install prisma @prisma/client
   ```

2. Initialize Prisma:
   ```bash
   npx prisma init
   ```

3. Update the `.env` file with your database URL (e.g., SQLite):
   ```env
   DATABASE_URL="file:./dev.db"
   ```

---

## Step 3: Define the Database Schema

1. Open `prisma/schema.prisma`.
2. Define your model. For example, a `Bug` model:
   ```prisma
   enum Status {
     OPEN
     IN_PROGRESS
     RESOLVED
     CLOSED
   }

   model Bug {
     id          Int      @id @default(autoincrement())
     title       String
     description String
     status      Status   @default(OPEN)
     createdAt   DateTime @default(now())
   }
   ```

3. Apply the schema changes:
   ```bash
   npx prisma migrate dev --name init
   ```

---

## Step 4: Generate and Configure Modules, Services, and Controllers

### 4.1: Create a Module for Prisma

1. Generate a Prisma module:
   ```bash
   nest generate module prisma
   ```

2. Create a `PrismaService`:
   ```typescript
   // src/prisma/prisma.service.ts
   import { Injectable, OnModuleInit, OnModuleDestroy } from '@nestjs/common';
   import { PrismaClient } from '@prisma/client';

   @Injectable()
   export class PrismaService extends PrismaClient implements OnModuleInit, OnModuleDestroy {
       async onModuleInit() {
           await this.$connect();
       }

       async onModuleDestroy() {
           await this.$disconnect();
       }
   }
   ```

3. Update the `PrismaModule` to provide the `PrismaService`:
   ```typescript
   // src/prisma/prisma.module.ts
   import { Module } from '@nestjs/common';
   import { PrismaService } from './prisma.service';

   @Module({
       providers: [PrismaService],
       exports: [PrismaService],
   })
   export class PrismaModule {}
   ```

### 4.2: Create the CRUD Module

1. Generate a module, service, and controller for your entity (e.g., Bugs):
   ```bash
   nest generate module bugs
   nest generate service bugs
   nest generate controller bugs
   ```

2. Update the `BugsModule` to import `PrismaModule`:
   ```typescript
   // src/bugs/bugs.module.ts
   import { Module } from '@nestjs/common';
   import { PrismaModule } from '../prisma/prisma.module';
   import { BugsService } from './bugs.service';
   import { BugsController } from './bugs.controller';

   @Module({
       imports: [PrismaModule],
       providers: [BugsService],
       controllers: [BugsController],
   })
   export class BugsModule {}
   ```

---

## Step 5: Implement CRUD Logic

### 5.1: Update the Service for Database Operations

```typescript
// src/bugs/bugs.service.ts
import { Injectable } from '@nestjs/common';
import { PrismaService } from '../prisma/prisma.service';
import { Prisma, Status } from '@prisma/client';

@Injectable()
export class BugsService {
    constructor(private readonly prisma: PrismaService) {}

    create(data: Prisma.BugCreateInput) {
        return this.prisma.bug.create({ data });
    }

    findAll() {
        return this.prisma.bug.findMany();
    }

    findOne(id: number) {
        return this.prisma.bug.findUnique({ where: { id } });
    }

    update(id: number, data: Prisma.BugUpdateInput) {
        return this.prisma.bug.update({ where: { id }, data });
    }

    remove(id: number) {
        return this.prisma.bug.delete({ where: { id } });
    }
}
```

### 5.2: Define Routes in the Controller

```typescript
// src/bugs/bugs.controller.ts
import { Controller, Get, Post, Body, Param, Patch, Delete } from '@nestjs/common';
import { BugsService } from './bugs.service';
import { Prisma } from '@prisma/client';

@Controller('bugs')
export class BugsController {
    constructor(private readonly bugsService: BugsService) {}

    @Post()
    create(@Body() createBugDto: Prisma.BugCreateInput) {
        return this.bugsService.create(createBugDto);
    }

    @Get()
    findAll() {
        return this.bugsService.findAll();
    }

    @Get(':id')
    findOne(@Param('id') id: number) {
        return this.bugsService.findOne(+id);
    }

    @Patch(':id')
    update(@Param('id') id: number, @Body() updateBugDto: Prisma.BugUpdateInput) {
        return this.bugsService.update(+id, updateBugDto);
    }

    @Delete(':id')
    remove(@Param('id') id: number) {
        return this.bugsService.remove(+id);
    }
}
```

---

## Step 6: Import BugsModule in AppModule

Update `src/app.module.ts`:

```typescript
import { Module } from '@nestjs/common';
import { PrismaModule } from './prisma/prisma.module';
import { BugsModule } from './bugs/bugs.module';

@Module({
  imports: [PrismaModule, BugsModule],
})
export class AppModule {}
```

---

## Step 7: Test the CRUD Endpoints

1. Start the development server:
   ```bash
   npm run start:dev
   ```

2. Test the following endpoints using **Postman**, **cURL**, or any API testing tool:
   - **POST** `/bugs`:
     ```json
     {
         "title": "Fix Login Bug",
         "description": "Login fails for some users",
         "status": "IN_PROGRESS"
     }
     ```
   - **GET** `/bugs`

   - **GET** `/bugs/:id`

   - **PATCH** `/bugs/:id`:
     ```json
     {
         "status": "RESOLVED"
     }
     ```

   - **DELETE** `/bugs/:id`

---

## Additional Enhancements

1. **Validation**: Use `class-validator` and `class-transformer` to add DTOs for request validation.
2. **Error Handling**: Implement global error handling for better API responses.
3. **Authentication**: Add authentication and authorization to secure endpoints.

---

This document provides a step-by-step guide to create a CRUD application in **Nest.js** with **Prisma**. Feel free to adapt it for other entities or use cases! 🚀
