# PLAN.md: betalocker

## Summary
betalocker is a local-first Android chastity game app built with Kotlin and Jetpack Compose. It combines a persistent lock timer, validated missions, BetaTokens, gambling machines, proof checks, Prejac Training, and a future local Censor Vault. Gemini can validate missions or proof, but the Android app owns all timer, token, and rule state.

## Current Product Direction
- Visual style: dark gray/near-black UI with deep cyan highlights.
- Core loop: lock timer -> validated missions -> BetaTokens -> gambling/shop -> timer changes.
- Navigation: bottom nav for primary flow, hamburger drawer for secondary tools.
- Data: Room database for structured local state, with Gemini API key kept in preferences.
- Scope boundary: no real money, no remote keyholder, no automatic media scraping, no video censor editor for now.

## Implemented Systems
- Custom lock setup followed by an in-app guided tour that changes screens, spotlights real controls, and finishes with Gemini setup in Settings.
- Persistent lock timer, strict mode, proof freezing, and Settings reset.
- Mission board with Gemini validation, cached validation results, draft/rejected states, and once-per-day BetaToken payout.
- BetaToken economy with case/gambling costs and shop mercy.
- Gambling tab with cases, roulette, cage tower, lock drop, result modals, and double-or-nothing.
- Prejac Training with local image/video picker, session media lock, failure penalties, and 24h hard-fail lockout.
- Gemini proof checks with model/API key settings and proof history.
- Room-backed persistence for app meta, missions, history, proof logs, and token transactions.

## UX / Navigation Plan
- Bottom nav keeps only primary tabs: Home, Task, Gamble, Prejac.
- Hamburger drawer owns secondary screens: History, Shop, Censor Vault, Settings.
- Top app bar shows app title, BetaToken balance, and menu button.
- Settings should stay grouped and compact, not a long everything-page.
- Tutorial can be replayed from Settings.

## Room Data Plan
- Room is the main local storage layer.
- Legacy JSON state migrates into Room on first launch after upgrade.
- Existing saved boxes migrate to BetaTokens at `1 old box = 3 BetaTokens`.
- Gemini API key remains outside Room in preferences.
- Future Room tables should cover Censor Vault image records and censor overlay metadata.

## BetaToken Rules
- Mission rewards:
  - Easy: `5 BetaTokens`
  - Medium: `7 BetaTokens`
  - Hard: `10 BetaTokens`
  - Hardcore: `20 BetaTokens`
- Costs:
  - Case: `3 BetaTokens`
  - Roulette: `5 BetaTokens`
  - Cage Tower: `6 BetaTokens`
  - Lock Drop: `5 BetaTokens`
- Shop mercy:
  - `-2 hours`: `40 BetaTokens`
  - `-4 hours`: `80 BetaTokens`
  - `-6 hours`: `115 BetaTokens`

## Future Roadmap
- Build Censor Vault:
  - local image import
  - black bar / pixel block / text caption overlays
  - saved overlay metadata
  - use censored images in Prejac Training
- Add richer mission templates and mission categories.
- Add better history filters and token ledger view.
- Add optional notification nudges/taunts.
- Consider moving more runtime state into ViewModel/repository structure after the single-file prototype stabilizes.
