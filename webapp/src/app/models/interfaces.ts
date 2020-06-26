import {HttpHeaders} from "@angular/common/http";

export interface User {
  id?: number,
  username: string,
  password?: string,
  firstname?: string,
  lastname?: string,
  phone?: string,
  country?: string,
  city?: string,
  hobbySet?: Hobby[]
}

export interface HttpError {
  error: {
    status: number,
    message: string
  },
  headers: HttpHeaders,
  message: string,
  name: string,
  ok: boolean,
  status: number,
  statusText: string,
  url: string
}

export interface Hobby {
  id: number,
  name: string
}

export interface Match {
  naam: string,
  phone: string,
  city: string,
  country: string
}

export interface TokenResponse {
  token: string;
}
