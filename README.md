# Agri CareTrade Mastitis Cost Calculator – Android

This is the standalone Android project for the Agri CareTrade Mastitis Cost Calculator.

## Step 1 complete
- Official Agri CareTrade logo embedded in the app.
- Agri CareTrade navy and green brand colours applied throughout.
- Calculator runs locally inside the Android app and no longer depends on the ChatGPT-hosted calculator URL.
- Screens included: Home, Calculator, Results Summary, Detailed Breakdown, Discuss Your Results, History, About, Print/Save Report.
- Results can be emailed to `derek.patterson@agricaretrade.com` with the subject `Help Required With Mastitis On My Farm`.
- Android print service is used for printing or saving the report as PDF.
- Results can be shared through Android's share sheet.
- Local calculation history is retained on the device.
- Launcher icon is derived from the official Agri CareTrade globe mark.

## Build a test APK with Codemagic
Use the `android-test-build` workflow in `codemagic.yaml`.

## Build a Play Store bundle
Use the `android-playstore-aab` workflow. Before production release, configure Play App Signing / release signing in Codemagic or Play Console.

## App ID
`com.agricaretrade.mastitis`

## Android targets
- Minimum SDK 23
- Target SDK 36
- Compile SDK 36
