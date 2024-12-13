import { Injectable } from '@nestjs/common';
import { CreateReceipeDto } from './dto/create-receipe.dto';
import { UpdateReceipeDto } from './dto/update-receipe.dto';
import { PrismaService } from '../prisma/prisma.service';

@Injectable()
export class ReceipeService {
  constructor(private readonly prisma: PrismaService) {}

  create(createReceipeDto: CreateReceipeDto) {
    return this.prisma.recipe.create({
      data: createReceipeDto,
    });
  }

  findAll() {
    return this.prisma.recipe.findMany();
  }

  findOne(id: number) {
    return this.prisma.recipe.findUnique({
      where: { id },
    });
  }

  update(id: number, updateReceipeDto: UpdateReceipeDto) {
    return this.prisma.recipe.update({
      where: { id },
      data: updateReceipeDto,
    });
  }

  remove(id: number) {
    return this.prisma.recipe.delete({
      where: { id },
    });
  }
}
