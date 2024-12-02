import { Module } from '@nestjs/common';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { PrismaModule } from './prisma/prisma.module';
import { BugsModule } from './bugs/bugs.module';

@Module({
  imports: [PrismaModule, BugsModule],
  controllers: [AppController],
  providers: [AppService],
})
export class AppModule {}
