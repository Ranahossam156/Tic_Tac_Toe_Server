# XO Game Server

## Description

The server for the XO Game is a crucial component that manages the online gameplay experience. It allows players to connect, interact, and compete in real-time. The server provides an interface with simple controls and a graphical overview of player statuses.

## Features

1. **Server Control**
   - **Start Server:** A button to initialize and start the server, making it ready to handle client connections.
   - **Close Server:** A button to shut down the server gracefully.

2. **Player Statistics Graph**
   - Displays a graphical representation of:
     - **Online Players:** Number of players currently connected and active.
     - **Available Players:** Players who are online but not currently in a game.
     - **Offline Players:** Players who are registered but not currently connected.

3. **Real-Time Updates**
   - The graph dynamically updates to reflect the current state of players as they connect, disconnect, or change statuses.

## How to Use

1. **Starting the Server**
   - Open the server application.
   - Click the "Start Server" button to begin listening for client connections.

2. **Monitoring Players**
   - View the real-time graph to monitor the number of online, available, and offline players.

3. **Shutting Down the Server**
   - Click the "Close Server" button to safely terminate the server. All player connections will be closed.

## Technical Details

- **Programming Language:** Java
- **Framework:** JavaFX (for user interface)
- **IDE:** NetBeans 8.2
- **Graph Implementation:** Uses JavaFX charts to display player statistics.
- **Networking:** Handles client-server communication using sockets.

## Getting Started

1. Clone the server repository:
   ```bash
   git clone https://github.com/Ranahossam156/Tic_Tac_Toe_Server.git
   ```

2. Open the project in NetBeans 8.2.

3. Build and run the project.

4. Use the interface to start and monitor the server.

## Contact

For any questions or feedback, feel free to reach out to the development team:
- Mustafa Mahmoud
- Khalid Amr
- Rana Hossam
- Moaz Mamdouh

---

Enjoy managing the XO Game Server!

