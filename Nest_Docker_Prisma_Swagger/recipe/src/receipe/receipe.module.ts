import { Module } from '@nestjs/common';
import { ReceipeService } from './receipe.service';
import { ReceipeController } from './receipe.controller';
import {PrismaModule} from "../prisma/prisma.module";

@Module({
  imports: [PrismaModule],
  controllers: [ReceipeController],
  providers: [ReceipeService],
})
export class ReceipeModule {}
