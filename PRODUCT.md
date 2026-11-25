# Product

<!-- impeccable:product-schema 1 -->

## Platform

android

## Users
Students and young professionals who want to log daily spending fast and see where their money goes.
For the website: recruiters and engineers looking at the author's portfolio, plus anyone who wants to
try the APK. (Website approach confirmed by the author, Nov 2026: landing page + APK download.)

## Product Purpose
A local-first Android budget tracker: log an expense or income in a few taps, or in plain English
through an AI chat ("Boba tea 6$"), scan receipts, and read monthly and yearly charts. Success: a
visitor understands what the app does in seconds and can install it.

## Positioning
Logging by sentence (AI chat) and by photo (receipt OCR) on top of a fast keypad, with data kept on
the device in SQLite rather than a cloud account.

## Operating Context
Native Java app (min SDK 26, target 34), Room database, Retrofit/OkHttp, ML Kit OCR, MPAndroidChart.
Optional AI through OpenRouter or a Cloudflare Worker proxy (proxy/). The website is a static page on
GitHub Pages; the APK comes from GitHub Releases built by CI.

## Capabilities and Constraints
- Amounts are whole US dollars; categories are fixed lists.
- AI chat, receipt parsing, news and stock data need API keys; without keys the app shows sample data
  or a "not configured" message. Market data is not financial advice.
- Debug APK is unsigned for the Play Store; Android will ask to allow installs from this source.

## Brand Commitments
- Name: BudgetMate. Visual identity already in the app: deep midnight blue (#0A1128) with metallic gold
  (#D4AF37), green for income (#34D399), red for expenses (#F87171), figures in Manrope with tabular numerals (the app and the website share the face).

## Evidence on Hand
- Real screenshots captured by CI on an Android 14 emulator with seeded demo data: docs/screenshots/
  (home, manage, stats, chat, news). Demo data is synthetic and must be labelled as such.
- No user counts, ratings, reviews or store listing exist. Never invent them.

## Product Principles
1. Logging must take seconds.
2. The user's money data stays on their phone.
3. Show the real app, never mockups dressed as the app.

## Accessibility & Inclusion
WCAG AA contrast on the website; screenshots carry descriptive alt text.
