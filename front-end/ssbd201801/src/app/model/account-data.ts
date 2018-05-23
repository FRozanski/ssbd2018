export interface AccountData {
    city?: string,
    confirm?: boolean,
    country?: string,
    email?: string,
    firstName?: string,
    lastName?: string,
    login?: string,
    phone?: string,
    postalCode?: string,
    street?: string,
    streetNumber?: string
    numberOfLogins?: number,
    numberOfOrders?: number,
    numberOfProducts?: number,
    password?: string;
    password2?: string;
    roles?: string[];
    oldPass?: string;
    newPassOne?: string;
    newPassTwo?: string;
    id?: string;
    accountId?: string;
}
