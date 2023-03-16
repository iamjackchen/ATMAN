# ATMan
 A QR-based event attendance manager written in Java.

## Usage Instructions

The app consists of two sections, which may be accessed from the window menu. 

#### Data Editor

The data editor section allows you to input data for the attendees of the intended event.

<p align="center">
  <img align="center" src="https://raw.githubusercontent.com/iamjackchen/ATMAN/main/Screenshots/DataEditor.png" width="49%" />
  <img align="center"  src="https://raw.githubusercontent.com/iamjackchen/ATMAN/main/Screenshots/DataEditorActive.png" width="49%" /> 
</p>

ATMan has CSV integration: you can export your session as a CSV, modify it externally,
and then import it back into the session. CSV import/export can be accessed through the session menu.

Once you are done, you may select a subset of attendees to generate matching QR data for. 
Each attendee will receive a randomised string that serves as their "passcode" for the event.

Each passcode can be exported to a QR Code (as a PNG) and used as a digital ticket for the event.

ATMan has built-in email support for automated QR code distribution via email (using either 
attendee ID directly as an email address, or using the attendee ID added to a provided domain name, e.g. abcd@domain).
You may also choose to distribute tickets manually

** A note on Gmail. Gmail has limited support for external account access without two-factor authentication.
You will need to generate a custom app password from your Google account and use that password for ATMan's built-in
email handler 

#### Event Kiosk
.
The event kiosk is a live session where you can scan tickets via a connected webcam and mark attendees as present or absent.
Simply hold your ticket up to the camera, and the app will automatically recognise the QR code and match it to a registered attendee

![](https://raw.githubusercontent.com/iamjackchen/ATMAN/main/Screenshots/EventKiosk.png)

## Upcoming Features

 - Data analytics (for attendence demographics)
 - Extensive theming (ATMan currently has four themes built-in)
 - More user-friendly mail configuration
 - Custom attendee data parameters
