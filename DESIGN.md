---
name: BudgetMate
description: "Local-first Android budget tracker: midnight ground, metallic gold for what you press, mint and coral for money in and out."
colors:
  gold: "#D4AF37"
  gold-hi: "#EBCB6B"
  gold-hover-top: "#F3D885"
  gold-hover: "#DDB943"
  gold-ink: "#241A02"
  income: "#34D399"
  income-ink: "#04261A"
  expense: "#F87171"
  expense-ink: "#2A0606"
  midnight: "#0A1128"
  midnight-deep: "#070C1E"
  navy-surface: "#111827"
  navy-raised: "#172033"
  navy-hover: "#1E293B"
  hairline: "#1F2A44"
  hairline-strong: "#2A3A5A"
  white: "#FFFFFF"
  text-dim: "#E5E7EB"
  muted: "#A3ACBD"
typography:
  display:
    fontFamily: "Manrope, ui-sans-serif, system-ui, -apple-system, 'Segoe UI', Roboto, sans-serif"
    fontSize: "clamp(40px, 4.4vw, 62px)"
    fontWeight: 800
    lineHeight: 1.02
    letterSpacing: "-0.035em"
  headline:
    fontFamily: "Manrope, ui-sans-serif, system-ui, -apple-system, 'Segoe UI', Roboto, sans-serif"
    fontSize: "clamp(30px, 3vw, 42px)"
    fontWeight: 800
    lineHeight: 1.1
    letterSpacing: "-0.02em"
  title:
    fontFamily: "Manrope, ui-sans-serif, system-ui, -apple-system, 'Segoe UI', Roboto, sans-serif"
    fontSize: "24px"
    fontWeight: 800
    letterSpacing: "-0.02em"
  body:
    fontFamily: "Manrope, ui-sans-serif, system-ui, -apple-system, 'Segoe UI', Roboto, sans-serif"
    fontSize: "17px"
    fontWeight: 400
    lineHeight: 1.6
  body-sm:
    fontFamily: "Manrope, ui-sans-serif, system-ui, -apple-system, 'Segoe UI', Roboto, sans-serif"
    fontSize: "14px"
    fontWeight: 400
    lineHeight: 1.6
  label:
    fontFamily: "Manrope, ui-sans-serif, system-ui, -apple-system, 'Segoe UI', Roboto, sans-serif"
    fontSize: "14px"
    fontWeight: 600
    lineHeight: 1
  button:
    fontFamily: "Manrope, ui-sans-serif, system-ui, -apple-system, 'Segoe UI', Roboto, sans-serif"
    fontSize: "16px"
    fontWeight: 700
    lineHeight: 1
  caps:
    fontFamily: "Manrope, ui-sans-serif, system-ui, -apple-system, 'Segoe UI', Roboto, sans-serif"
    fontSize: "12px"
    fontWeight: 700
    lineHeight: 1.2
    letterSpacing: "0.06em"
  figure-hero:
    fontFamily: "Manrope, ui-sans-serif, system-ui, -apple-system, 'Segoe UI', Roboto, sans-serif"
    fontSize: "52px"
    fontWeight: 700
    lineHeight: 1
    letterSpacing: "-0.03em"
    fontFeature: "tnum"
  figure-total:
    fontFamily: "Manrope, ui-sans-serif, system-ui, -apple-system, 'Segoe UI', Roboto, sans-serif"
    fontSize: "44px"
    fontWeight: 700
    lineHeight: 1.1
    letterSpacing: "-0.03em"
    fontFeature: "tnum"
  figure-md:
    fontFamily: "Manrope, ui-sans-serif, system-ui, -apple-system, 'Segoe UI', Roboto, sans-serif"
    fontSize: "20px"
    fontWeight: 700
    lineHeight: 1.3
    fontFeature: "tnum"
  figure:
    fontFamily: "Manrope, ui-sans-serif, system-ui, -apple-system, 'Segoe UI', Roboto, sans-serif"
    fontSize: "16px"
    fontWeight: 700
    lineHeight: 1
    fontFeature: "tnum"
  figure-key:
    fontFamily: "Manrope, ui-sans-serif, system-ui, -apple-system, 'Segoe UI', Roboto, sans-serif"
    fontSize: "22px"
    fontWeight: 700
    lineHeight: 1
    fontFeature: "tnum"
  figure-mono:
    fontFamily: "'JetBrains Mono', ui-monospace, SFMono-Regular, Menlo, Consolas, monospace"
    fontSize: "16px"
    fontWeight: 700
    lineHeight: 1
    fontFeature: "tnum"
rounded:
  tag: "6px"
  field: "12px"
  key: "14px"
  card: "18px"
  screen: "22px"
  pill: "999px"
spacing:
  chip-gap: "8px"
  key-gap: "10px"
  row: "18px"
  card: "20px"
  card-wide: "28px"
  grid: "32px"
  grid-wide: "48px"
  stack: "40px"
  gutter: "32px"
  gutter-mobile: "16px"
  section: "88px"
  section-mobile: "64px"
components:
  button-gold:
    backgroundColor: "{colors.gold}"
    textColor: "{colors.gold-ink}"
    typography: "{typography.button}"
    rounded: "{rounded.key}"
    padding: "0 20px"
    height: "48px"
  button-gold-hover:
    backgroundColor: "{colors.gold-hover}"
    textColor: "{colors.gold-ink}"
  button-ghost:
    backgroundColor: "transparent"
    textColor: "{colors.white}"
    typography: "{typography.button}"
    rounded: "{rounded.key}"
    padding: "0 20px"
    height: "48px"
  button-ghost-hover:
    backgroundColor: "{colors.navy-raised}"
    textColor: "{colors.white}"
  key:
    backgroundColor: "{colors.navy-raised}"
    textColor: "{colors.white}"
    typography: "{typography.figure-key}"
    rounded: "{rounded.key}"
    height: "58px"
  key-hover:
    backgroundColor: "{colors.navy-hover}"
  key-save:
    backgroundColor: "{colors.gold}"
    textColor: "{colors.gold-ink}"
    rounded: "{rounded.key}"
  chip:
    backgroundColor: "{colors.navy-raised}"
    textColor: "{colors.text-dim}"
    typography: "{typography.label}"
    rounded: "{rounded.pill}"
    padding: "9px 13px"
  chip-selected:
    backgroundColor: "{colors.gold}"
    textColor: "{colors.gold-ink}"
  segment-track:
    backgroundColor: "{colors.midnight-deep}"
    rounded: "{rounded.pill}"
    padding: "3px"
  segment-expense-active:
    backgroundColor: "{colors.expense}"
    textColor: "{colors.expense-ink}"
    rounded: "{rounded.pill}"
    padding: "8px 14px"
  segment-income-active:
    backgroundColor: "{colors.income}"
    textColor: "{colors.income-ink}"
    rounded: "{rounded.pill}"
    padding: "8px 14px"
  card:
    backgroundColor: "{colors.navy-surface}"
    rounded: "{rounded.card}"
    padding: "20px"
  input:
    backgroundColor: "{colors.midnight-deep}"
    textColor: "{colors.white}"
    rounded: "{rounded.field}"
    padding: "0 14px"
    height: "46px"
  tag:
    textColor: "{colors.muted}"
    rounded: "{rounded.tag}"
    padding: "5px 8px"
  nav-link:
    textColor: "{colors.muted}"
    typography: "{typography.label}"
  screenshot:
    backgroundColor: "{colors.navy-surface}"
    rounded: "{rounded.screen}"
---

# Design System: BudgetMate

## Overview

**Creative North Star: "The Midnight Ledger"**

BudgetMate is a ledger kept at night. A midnight-blue ground, navy surfaces raised by a single tone step and a hairline, and one metallic gold that marks the thing you press. Money speaks in two colors only, mint for what comes in and coral for what goes out, and every figure sits in tabular columns so totals line up and digits hold still while you type. The Android app established this world; the website inherits it rather than inventing a marketing skin, and its hero is the app's own keypad and ledger at app scale.

Density is calm outside and tight inside. Sections breathe (88px of padding above and below, each opened by a hairline), but the interactive objects keep app spacing, 10px between keys and 8px between chips, because they are tools a visitor uses, not illustrations of one. Depth comes from tone and from a 1px highlight along the top edge of anything raised, with one long, soft shadow under cards. The world is dark only (`color-scheme: dark`).

Facts are set as rows divided by hairlines; cards are kept for objects you can use. The app is shown through real, frameless screenshots, never a drawn phone.

**Key Characteristics:**
- Midnight ground with navy surfaces stepped by tone, a 1px hairline on every raised edge.
- Metallic gold as the single action color, run as a top-lit gradient on everything you press.
- Mint and coral reserved for signed money: in and out, up and down.
- Manrope for every word; tabular figures for every amount.
- The keypad and the day-grouped ledger, at app scale, as the signature objects.
- Hairline-ruled definition rows in place of feature-card grids.

## Colors

A night-blue tonal stack, one metal, and two money colors.

### Primary
- **Metallic Gold** (#D4AF37): fills the pressables that matter (Download APK, the tall SAVE key, the selected category chip) and colors the one figure that sums things up, the month's net. As text it also carries the emphasized phrase in the hero headline and the terms of the privacy rows. On fills it is the base of a top-lit gradient from Gold Highlight.
- **Gold Highlight** (#EBCB6B): the top stop of every gold fill, the link color, and the 2px focus ring.
- **Lit Gold** (#F3D885 over #DDB943): the hover gradient for gold pressables, one step brighter than rest.
- **Gold Ink** (#241A02): text and icons on gold, at 8.2:1 on Metallic Gold. Also the text-selection color on a gold selection.

### Secondary
- **Income Mint** (#34D399): money in and figures going up. Income amounts and totals, the keypad amount while Income is selected, the active Income segment. 9.2:1 on Raised Navy.
- **Mint Ink** (#04261A): text on the active Income segment.

### Tertiary
- **Expense Coral** (#F87171): money out and figures going down. Expense amounts and totals, the keypad amount while Expense is selected, the active Expense segment. 6.4:1 on Raised Navy.
- **Coral Ink** (#2A0606): text on the active Expense segment.

### Neutral
- **Midnight Ground** (#0A1128): the page, the browser theme color, and the sticky top bar at 92% over a blur.
- **Night Well** (#070C1E): recessed places: the segmented-control track, the sentence field and its readout, the closing band and the footer.
- **Raised Navy** (#111827): cards (keypad, ledger, sentence demo) and the placeholder behind screenshots.
- **Key Navy** (#172033): keypad keys, idle chips, the ghost button's hover fill.
- **Pressed Slate** (#1E293B): key hover.
- **Ledger Rule** (#1F2A44): the 1px hairline everywhere: card borders, ledger row dividers, section top borders, the top bar's lower edge.
- **Strong Rule** (#2A3A5A): borders on things you press or read as an object (chips, ghost button, inputs, tags, screenshots) and the top rule of definition lists.
- **Headline White** (#FFFFFF): headings, entry titles, key digits, definition terms.
- **Soft White** (#E5E7EB): body text, at 14.3:1 on Raised Navy.
- **Fog Grey** (#A3ACBD): secondary text: the lede and section intros, captions, meta lines, nav links, idle segments, tags, ledger labels. 7.8:1 on Raised Navy.

### Named Rules
**The Gold Means Press Rule.** Solid gold fills only what you press or have selected: Download APK, SAVE, the selected chip. Everywhere else gold is text (the net total, the hero's emphasis, links, row terms) or a low-alpha wash (the 12% gold that marks a just-saved ledger row). No gold panels, bands or section backgrounds.

**The Sign Color Rule.** Mint means in or up and coral means out or down, and they color only signed figures and the control that sets the sign. Every signed amount carries its sign (+, or the true minus − rather than a hyphen) and the color that matches it.

**The Own Ink Rule.** Text on a colored fill uses that color's ink (Gold Ink, Mint Ink, Coral Ink), never white.

## Typography

**Display Font:** Manrope (with ui-sans-serif, system-ui, -apple-system, Segoe UI, Roboto)
**Body Font:** Manrope (same stack)
**Figure Font:** Manrope with tabular figures (`tnum`). The website still sets its figures in JetBrains Mono (with ui-monospace, SFMono-Regular, Menlo, Consolas), recorded below as the figure-mono holdover.

**Character:** One family carries the product, the same face in the app (res/font/manrope*.ttf, weights 400 to 800) and on the website. Words are heavy and tightly tracked in headings, plain at 400 in body; figures are the second voice, set tabular so a column of amounts reads like a ledger.

### Hierarchy
- **Display** (800, clamp(40px, 4.4vw, 62px), 1.02, -0.035em): the hero headline only, set in two lines, with one phrase in Metallic Gold. 38px under 760px.
- **Headline** (800, clamp(30px, 3vw, 42px), 1.1, -0.02em): section headings. The closing call runs a touch larger, clamp(30px, 3.2vw, 44px), held to 18ch.
- **Title** (800, 24px, -0.02em): headings inside wide cards. Lead definition terms sit at 22px (-0.01em), card headings at 20px, secondary terms at 18px.
- **Body** (400, 17px, 1.6): running text in Soft White, intros in Fog Grey at 62ch, the hero lede at 46ch and 18px. Body drops to 16px under 760px.
- **Body Small** (400, 14px, 1.6): fine print, captions, provenance notes. Meta lines under ledger entries and the keypad date run at 13px.
- **Label** (600, 14px, 1): chips and field labels. Nav links are 15px, tags 12px, segment buttons 700 at 13px.
- **Button** (700, 16px, 1): sentence case, as in the app ("Download APK", never all caps).
- **Caps** (700, 12px, 0.06em, uppercase): ledger day headers only (TODAY, YESTERDAY), with the day's subtotal right-aligned in tabular figures.
- **Figures** (700, tabular): the keypad amount at 52px (44px on mobile, -0.03em), the ledger net at 44px, split totals at 20px, entry amounts at 16px, key digits at 22px.

### Figure faces in use
- **App:** Manrope with `tnum` on every figure (`fontFeatureSettings="tnum"`; keypad digits through BMKeypadDigit, 24sp bold). This is the system default.
- **Website holdover:** site/index.html sets every figure in JetBrains Mono at the sizes above (700; key digits at 500), loaded from Google Fonts at 500 and 700. It is tabular by construction, so it satisfies the rule below. JetBrains Mono appears nowhere else on the site except the sentence demo, where the typed sentence (500, 16px) and its JSON readout (500, 14px, 1.5) are set in it. New work on the site matches JetBrains Mono for figures until the site moves to Manrope; new work anywhere else uses Manrope `tnum`.

### Named Rules
**The Tabular Rule.** Every money figure is tabular: amounts, totals, day subtotals, keypad digits, market changes. Digits keep their width while you type and amounts align on the right. In Manrope that means `tnum` (`font-variant-numeric: tabular-nums` on the web); never proportional figures for an amount.

**The Ledger Caps Rule.** Uppercase with tracking appears in two places, both inherited from the app: ledger day headers (0.06em) and the SAVE key (0.12em). It is never an eyebrow or kicker above a heading.

## Layout

A single 1280px column with 32px side gutters (16px under 760px), under a 64px sticky top bar.

- **Hero:** a headline row across the full width (headline left at 1.15fr, lede and buttons right at 1fr, bottom-aligned), then the keypad card at a fixed 460px beside the ledger card, tops aligned, 28px row gap and 32px column gap. Under 1180px the two cards share equal columns and the copy stacks; under 760px everything is one column with 20px gaps.
- **Sections:** 88px top and bottom (64px under 760px), each opened by a 1px Ledger Rule top border. A headline, an optional Fog Grey intro at 62ch, then content 40px below. The closing band is 96px and sits on Night Well with the footer.
- **Grids:** asymmetric two-column splits at 5:7 (ways to log, privacy), a 1 : 1 : 0.9 three-column month view, 32px gaps, widening to 48px in the privacy split and the two-column build list. Under 1180px they fall to one column, or two with facts spanning.
- **Gallery:** five equal columns at desktop; under 1180px a horizontal scroll-snap strip of 240px screenshots, 72vw on mobile.
- **Rhythm:** 8, 10, 12 and 14px inside objects; 20px card padding (28px for wide cards, 22px on mobile); 16 to 22px row padding; 32 and 48px grid gaps; 40px from heading to content; 88 to 96px between sections.

### Named Rules
**The Rows Not Cards Rule.** Facts are definition rows: a term column (140 to 180px, or stacked on mobile) and a description, divided by Ledger Rule hairlines under a Strong Rule top rule. Cards hold only things you use or that reproduce the app: the keypad, the ledger, the sentence demo.

## Elevation & Depth

A hybrid that leans on tone. The stack runs Night Well, Midnight Ground, Raised Navy, Key Navy, Pressed Slate, and each step up gets a 1px hairline. What makes a surface read as raised is a 1px white inset along its top edge, the same inner highlight the app's keypad and hero cards carry. Cards add one long, soft ambient shadow; keys add a 2px lip; gold adds a tighter drop. Nothing uses colored glows on the website.

### Shadow Vocabulary
- **Card** (`box-shadow: 0 1px 0 rgba(255,255,255,.04) inset, 0 24px 48px -28px rgba(0,0,0,.9)`): every card. The drop sits far below and mostly hidden, so cards lift without a halo.
- **Key** (`box-shadow: 0 1px 0 rgba(255,255,255,.10) inset, 0 2px 0 rgba(0,0,0,.35)`): keypad keys only. The 2px lip is the key-cap edge.
- **Gold** (`box-shadow: 0 1px 0 rgba(255,255,255,.35) inset, 0 8px 20px -10px rgba(0,0,0,.8)`): gold buttons.
- **Focus** (`outline: 2px solid #EBCB6B; outline-offset: 3px`): every focusable element.
- **Top bar** (`background: rgba(10,17,40,.92); backdrop-filter: saturate(140%) blur(10px)`): the sticky bar, over a 1px Ledger Rule lower edge.

### Named Rules
**The Inner Highlight Rule.** Raised things are lit from above by a 1px white inset on the top edge: 4% on cards, 10% on keys, 35% on gold. That highlight, not a bigger shadow, is what lifts a surface.

## Shapes

Soft, consistent corners scaled to the object: 6px tags, 12px fields and readouts, 14px buttons and keys (the app keys run 12dp), 18px cards and month-view images, 22px screenshots, full pills for chips and the segmented control. Every raised or pressable shape carries a 1px border. Screenshots are shown frameless: the image itself, rounded and bordered, with no drawn device. Icons are inline SVG strokes in currentColor, 18 to 24px, stroke 2 to 2.6 with round caps and joins.

## Components

### Buttons
Confident, top-lit, and few.
- **Shape:** gently rounded (14px), 48px tall, 20px side padding, 10px gap to an 18px icon. 44px in the mobile hero, 40px in the mobile top bar.
- **Gold (primary):** a top-lit gradient from Gold Highlight to Metallic Gold, Gold Ink text at 700 16px, the gold shadow. Used for Download APK and nothing that competes with it.
- **Ghost (secondary):** transparent with a 1px Strong Rule border and Headline White text; used for the GitHub source and the releases list.
- **Hover / Focus / Active:** gold brightens to Lit Gold; ghost fills with Key Navy. Focus draws the 2px Gold Highlight ring at 3px offset. Press nudges down 1px. Transitions run 200ms on cubic-bezier(.16, 1, .3, 1).

### Chips
- **Style:** Key Navy pills with a 1px Strong Rule border, Soft White text at 600 14px, 9px by 13px padding, 8px apart.
- **State:** single select. The selected chip fills with Metallic Gold, Gold Ink text, gold border. The set swaps with the entry type (expense categories or income categories).

### Segmented Control
- **Style:** a Night Well pill track with a 1px Ledger Rule border and 3px inset; buttons are 700 13px Fog Grey.
- **State:** the active Expense segment fills coral with Coral Ink; the active Income segment fills mint with Mint Ink. Switching recolors the amount over 200ms and swaps the chips.

### Cards / Containers
- **Corner Style:** 18px.
- **Background:** Raised Navy.
- **Shadow Strategy:** the card shadow (see Elevation & Depth).
- **Border:** 1px Ledger Rule.
- **Internal Padding:** 20px; 28px for wide content cards (22px on mobile).

### Inputs / Fields
- **Style:** Night Well fill, 1px Strong Rule border, 12px radius, 46px tall, 14px side padding, Headline White text. The label above is 600 14px Soft White.
- **Readout:** a Night Well block under the field with a 1px Ledger Rule border and 12px radius, at least 44px tall, announcing its result politely to assistive tech.
- **Focus:** the global Gold Highlight ring.

### Navigation
- **Style:** a 64px sticky bar on translucent Midnight Ground with blur and a Ledger Rule lower edge. Brand left (32px icon at 9px radius, Manrope 800 18px), section links at 600 15px Fog Grey turning Headline White on hover, the gold Download APK pushed right.
- **Mobile:** under 760px the section links hide; brand and a 40px Download button remain.

### Keypad (signature)
The app's Home keypad, usable on the page.
- **Amount display:** the amount at hero figure size in the color of the entry type, signed, over a Fog Grey 13px date line and a 1px Ledger Rule.
- **Keys:** a 3 by 4 grid of digits (with 00 and delete) plus a fourth column at 1.1fr where SAVE spans all four rows; 10px gaps. Keys are Key Navy, 1px Ledger Rule, 14px radius, the key shadow, Headline White digits at 22px. 58px tall (50px on mobile). Hover goes to Pressed Slate; press scales to 0.96 over 100ms.
- **SAVE:** the gold gradient, a check icon above SAVE in Manrope 800 15px at 0.12em, Gold Ink.
- **Behavior:** saving an empty amount shakes the display (6px, 350ms) and says why in the hint line below. Digits, Backspace and Enter work from the keyboard.

### Ledger (signature)
The app's month view in miniature.
- **Head:** a 700 16px Fog Grey title beside a Sample data tag.
- **Net:** the total figure size in Metallic Gold.
- **Split:** two columns between hairlines with a vertical rule; 13px Fog Grey labels over 20px figures in mint and coral.
- **Entries:** grouped under caps day headers with a tabular subtotal; each row is a 700 15px Headline White title over a 13px Fog Grey meta line, the signed amount right-aligned across both lines, rows divided by Ledger Rule.
- **New entry:** drops in from 10px above over 600ms with a 12% gold wash fading right.

### Tag
- **Style:** 600 12px Fog Grey, 1px Strong Rule border, 6px radius, 5px by 8px. Labels sample and demo data where it appears.

### Screenshot Gallery
- **Style:** real screenshots at 9:20, 22px radius, 1px Strong Rule border, Raised Navy behind while loading. Caption below: the tab name in 16px Headline White bold, then one 15px Fog Grey line. A provenance note closes the set.

## Do's and Don'ts

### Do:
- **Do** set every amount in tabular figures, signed (+, or − U+2212), in Income Mint (#34D399) or Expense Coral (#F87171) to match the sign.
- **Do** put each fill's own ink on it: Gold Ink (#241A02), Mint Ink (#04261A), Coral Ink (#2A0606).
- **Do** run gold fills as a top-lit gradient (#EBCB6B to #D4AF37) that brightens on hover (#F3D885 to #DDB943).
- **Do** give every raised surface a 1px hairline (#1F2A44, or #2A3A5A for pressables and objects) and a 1px white inset highlight on its top edge.
- **Do** set facts as hairline-ruled definition rows and keep cards for the keypad, the ledger and other objects people use.
- **Do** show the app through real, frameless screenshots at 22px radius with a 1px #2A3A5A border, and label demo data where it appears.
- **Do** keep the 2px #EBCB6B focus ring at 3px offset on everything focusable, and collapse motion under `prefers-reduced-motion`.

### Don't:
- **Don't** fill panels, bands or section backgrounds with solid gold; solid gold is for pressables and selection, and low-alpha gold only washes a highlight.
- **Don't** use mint or coral for decoration, links or anything other than a signed figure and the control that sets its sign.
- **Don't** set an amount in proportional figures.
- **Don't** put tracked uppercase eyebrows or kickers above headings; caps belong to ledger day headers and the SAVE key.
- **Don't** lay facts out as grids of icon feature cards.
- **Don't** draw phone frames or mockups around screenshots.
- **Don't** add hard offset shadows to cards or buttons; the 2px lip belongs to keypad keys alone.
- **Don't** put white text on gold, mint or coral fills.
