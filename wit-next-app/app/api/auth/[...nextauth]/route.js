import NextAuth from "next-auth"
import AzureAdProvider from "next-auth/providers/azure-ad"
import CredentialsProvider from "next-auth/providers/credentials"
import GithubProvider from 'next-auth/providers/github'
import axios from "axios";

const handler = NextAuth({
    secret: process.env.AUTH_SECRET,
    session: {
        strategy: 'jwt'
    },
    providers: [

        CredentialsProvider({
            name: 'credentials',

            credentials: {
                email: { label: "Email", type: "text" },
                password: { label: "Password", type: "password" },
            },

            async authorize(credentials){
                try{
                    const payload = {
                        username: credentials?.email,
                        password: credentials?.password,
                    };
                    const userTokens= await axios.post(`${process.env.API_ENDPOINT}/api/v1/auth/login`,
                       payload
                    )
                    console.log("Access Token",userTokens)
                    const userDetails= await axios.get(`${process.env.API_ENDPOINT}/api/v1/users/me`,{
                        headers: {
                            "Content-Type": "application/json",
                            "Accept-Language": "en-US",
                            Authorization: `Bearer ${userTokens?.accessToken}`,
                        },
                    })
                    const user = {
                        id: userDetails?.id,
                        email: userDetails?.email,
                        firstName: userDetails?.firstName,
                        lastName: userDetails?.lastName,
                        phone: userDetails?.phoneNumber,
                        accessToken: userTokens?.accessToken,
                        accessTokenExpires: Date.now() + userTokens?.expiresAt,
                        refreshToken: userTokens?.refreshToken,
                        roles: ["admin"]
                    };
                    return  user;
                }catch (error) {
                    console.warn("Error logging in", error);
                    return null;
                }
            }
        }),

        GithubProvider({
            clientId: process.env.GITHUB_CLIENT_ID ,
            clientSecret: process.env.GITHUB_CLIENT_SECRET ,
        }),
        AzureAdProvider({
        clientId: process.env.AZURE_AD_CLIENT_ID,
        clientSecret: process.env.AZURE_AD_CLIENT_SECRET,
        tenantId: process.env.AZURE_AD_TENANT_ID,

        authorization: {
            params: {
                scope: "openid email profile offline_access"
            }
        },

        httpOptions: {
            timeout: 50000
        }
    })],

    callbacks: {
        async jwt({token, account, profile}) {
            if (account) {
                console.log(profile)
                token.accessToken = account.access_token
                token.refreshToken = account.refresh_token
                token.idToken = account.id_token
                token.id = profile?.sub
            }
            return token
        },
        redirect: async (url, baseUrl) => {
            return baseUrl
        },
        async session({session, token, user}) {
            session = {
                ...session, ...{
                    accessToken: token.accessToken,
                    refreshToken: token.refreshToken,
                    idToken: token.idToken
                }
            }
            return session
        }
    },
})

export {handler as GET, handler as POST}