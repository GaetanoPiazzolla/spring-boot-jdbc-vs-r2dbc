import http from 'k6/http';
import { check, sleep } from 'k6';
import { Trend, Rate } from 'k6/metrics';

const getTrend = new Trend('Get Books');
const getErrorRate = new Rate('Get Books error');

const postTrend = new Trend('Add Book');
const postErrorRate = new Rate('Add Book error');

export default function () {
  const url = 'http://localhost:8080/books/'

  const params = {
    headers: {
      'Content-Type': 'application/json',
    },
  };

  const body = JSON.stringify({
      author: `Author Name ${__ITER}`,
      isbn: `${__VU}`,
      title: `always the same title`,
      year: 1900
  });

  const requests = {
      'Get Books': {
        method: 'GET',
        url: url,
        params: params,
      },
      'Add Book': {
        method: 'POST',
        url: url,
        params: params,
        body: body,
      },
    };

  const responses = http.batch(requests);
  const getResp = responses['Get Books'];
  const postResp = responses['Add Book'];

  check(getResp, {
    'status is 200': (r) => r.status === 200,
  }) || getErrorRate.add(1);

  getTrend.add(getResp.timings.duration);

  check(postResp, {
    'status is 200': (r) => r.status === 200,
  }) || postErrorRate.add(1);

  postTrend.add(postResp.timings.duration);

}