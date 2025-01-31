// import necessary modules
import { check } from "k6";
import http from "k6/http";

// define configuration
export const options = {
  // define thresholds
  thresholds: {
    http_req_failed: ["rate<0.01"], // http errors should be less than 1%
    http_req_duration: ["p(99)<1000"], // 99% of requests should be below 1s
  },
};

export default function () {
  // define URL and request body
  const url = "http://localhost:8081/api/v1/events";
  const payload = JSON.stringify({
    id: "payment-successful",
    projectId: "27dd42b8-b6a3-4868-9f51-2fbd0ca00300",
    title: "New successful payment",
    description: "A new payment done for Ethiopia coffee",
  });
  const params = {
    headers: {
      "Content-Type": "application/json",
      "x-eb-api-key": "i31mhdiWYlsEdYqMNu2Rn6NNciWN0QSufwsQP2ddJePEGlgBPBtZHGp3RYlyVfRy"
    },
  };

  // send a post request and save response as a variable
  const res = http.post(url, payload, params);

  // check that response is 200
  check(res, {
    "response code was 200": (res) => res.status == 200,
  });
}
