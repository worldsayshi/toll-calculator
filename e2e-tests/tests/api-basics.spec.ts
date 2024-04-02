import { test, expect } from '@playwright/test';

test('get-toll', async ({ request }) => {
    let res = await request.get(`/get-toll-fee?vehicle=Car&dates=${encodeURIComponent("2000-10-31T01:30:00.000-05:00")}`);
    let toll = await res.json();
    expect(toll).toBeGreaterThanOrEqual(0);
});