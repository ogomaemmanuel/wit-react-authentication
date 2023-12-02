import {NextRequest} from "next/server";
import { getServerSession } from "next-auth"
import axios from "axios";
export async function GET(request: NextRequest) {
    const session = await getServerSession();
    const res = await axios(`${process.env.API_ENDPOINT}/api/v1/blogs`, {
        headers: {
            Authorization: `Bearer ${session?.accessToken}`
        },
        next: { revalidate: 60 }, // Revalidate every 60 seconds
    })
    const data = await res.json()

    return Response.json(data)
}

export async function POST(request: NextRequest) {
    const res = await fetch('https://data.mongodb-api.com/...', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'API-Key': process.env.DATA_API_KEY,
        },
        body: JSON.stringify({ time: new Date().toISOString() }),
    })

    const data = await res.json()

    return Response.json(data)
}