export interface IProduct {
    id?: number;
    productName?: string;
    quantity?: number;
    price?: number;
}

export class Product implements IProduct {
    constructor(public id?: number, public productName?: string, public quantity?: number, public price?: number) {}
}
