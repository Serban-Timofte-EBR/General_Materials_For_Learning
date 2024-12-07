import {IsOptional, IsString} from 'class-validator';

export class CreateReceipeDto {
    @IsString()
    title: string;

    @IsOptional()
    @IsString()
    description?: string;

    @IsString()
    ingredients: string;

    @IsString()
    instructions: string;
}
