import { Module } from '@nestjs/common';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { PrismaModule } from './prisma/prisma.module';
import { ReceipeModule } from './receipe/receipe.module';

@Module({
  imports: [PrismaModule, ReceipeModule],
  controllers: [AppController],
  providers: [AppService],
})
export class AppModule {}
