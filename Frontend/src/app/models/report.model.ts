import {Pet} from "./pet.model";

export interface Report {
  id: number;
  title: string;
  description: string;
  latitude: number;
  longitude: number;
  city: string;
  address: string;
  type: string;
  status: string;
  additionalNotes: string;
  createdAt: Date;
  updatedAt: Date;
  verified: boolean;
  pet_id: number;
  user_id: number;
  pet: Pet;
  reporterName:string,
  user: any;
}
