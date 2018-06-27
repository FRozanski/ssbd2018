import { AccountData } from "../../mok/model/account-data";
import { OrderStatus } from "./order-status";
import { Shipping } from "./shipping";

export interface Order {
    id?: number,
    isClosed?: boolean,
    orderPlacedDate?: Date,
    totalPrice?: number,
    buyer?: AccountData
    seller?: AccountData,
    shipping: Shipping,
    status: OrderStatus

}