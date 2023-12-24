import axios from 'axios';
import base64 from 'base-64';


export class OAuthToken {
    constructor(client_id, client_secret) {
        this.client_id = client_id;
        this.client_secret = client_secret;
    }

    getBase64Encoding() {
        const credentials = `${this.client_id}:${this.client_secret}`;
        const base64String = base64.encode(credentials);
        return base64String;
    }

    async getApplicationToken() {
        const url = 'https://api.ebay.com/identity/v1/oauth2/token';

        const headers = {
            'Content-Type': 'application/x-www-form-urlencoded',
            'Authorization': `Basic ${this.getBase64Encoding()}`
        };

        const data = new URLSearchParams();
        data.append('grant_type', 'client_credentials');
        data.append('scope', 'https://api.ebay.com/oauth/api_scope');

        try {
            const response = await axios.post(url, data, { headers });
            return response.data.access_token;
        } catch (error) {
            console.error('Error obtaining access token:', error);
            throw error;
        }
    }
}

// // Usage example
// const client_id = 'Jianchen-csci571h-PRD-2727b8eb0-66ac97b7';
// const client_secret = 'PRD-727b8eb0f256-fdfa-4552-a0d8-aa1e';

// const oauthToken = new OAuthToken(client_id, client_secret);

// oauthToken.getApplicationToken()
//     .then((accessToken) => {
//         console.log('Access Token:', accessToken);
//     })
//     .catch((error) => {
//         console.error('Error:', error);
//     });
