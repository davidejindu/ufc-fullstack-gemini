import axios from "axios";

const REST_API_BASE_URL = import.meta.env.VITE_BACKEND_API_BASE_URL;

export const compareFighters = (fighter1, fighter2) => axios.get(REST_API_BASE_URL+`/compare/${fighter1}/${fighter2}`);
export const getFighersByDivison = divison => axios.get(REST_API_BASE_URL+`/division/${divison}`);
export const getFighersById= id => axios.get(REST_API_BASE_URL+`/id/${id}`);
export const getAllDivisions = () => axios.get(REST_API_BASE_URL+`/all-divisions`);