import { Injectable } from '@nestjs/common';
import { Prisma } from '@prisma/client';
import { PrismaService } from 'src/prisma/prisma.service';

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
