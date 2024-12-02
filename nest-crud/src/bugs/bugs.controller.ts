import {
  Controller,
  Get,
  Post,
  Body,
  Param,
  Patch,
  Delete,
} from '@nestjs/common';
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
