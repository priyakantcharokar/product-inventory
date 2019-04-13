import { IProduct } from 'app/shared/model/product.model';

export interface IWarehouse {
    id?: number;
    name?: string;
    location?: string;
    products?: IProduct[];
}

export class Warehouse implements IWarehouse {
    constructor(public id?: number, public name?: string, public location?: string, public products?: IProduct[]) {}
}
