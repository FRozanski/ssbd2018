export interface AccountData {
    id?: number;
    city?: string,
    confirm?: boolean,
    active?: boolean;
    country?: string,
    email?: string,
    name?: string,
    surname?: string,
    login?: string,
    phone?: string,
    postalCode?: string,
    street?: string,
    streetNumber?: string,
    flatNumber?: string;
    numberOfLogins?: number,
    numberOfOrders?: number,
    numberOfProducts?: number,
    password?: string;
    password2?: string;
    roles?: string[];
    oldPassword?: string;
    firstPassword?: string;
    secondPassword?: string;
    accountId?: number;
    version?: number;
    accessLevels?: string[];
    lastLoginDate?: string;
    lastLoginIp?: string;
}
