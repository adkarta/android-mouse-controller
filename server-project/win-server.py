import SocketServer
import win32api, win32con


class MyTCPHandler(SocketServer.BaseRequestHandler):
    """
    The RequestHandler class for our server.

    It is instantiated once per connection to the server, and must
    override the handle() method to implement communication to the
    client.
    """

    def move_mouse(self, x,y, click):
        win32api.SetCursorPos((int(float(x)),int(float(y))))
        print(click)
        if (click == "1"):
            win32api.mouse_event(win32con.MOUSEEVENTF_LEFTDOWN,int(float(x)),int(float(y)),0,0)
            win32api.mouse_event(win32con.MOUSEEVENTF_LEFTUP,int(float(x)),int(float(y)),0,0)
            print("arman")
	
    def handle(self):
        # self.request is the TCP socket connected to the client
        self.data = self.request.recv(1024).strip()
        print "{} wrote:".format(self.client_address[0])
        x,y,click = self.data.split("-")
        self.move_mouse(x, y, click)
        # just send back the same data, but upper-cased
        self.request.sendall(self.data.upper())

if __name__ == "__main__":
    HOST, PORT = "0.0.0.0", 9999

    # Create the server, binding to localhost on port 9999
    server = SocketServer.TCPServer((HOST, PORT), MyTCPHandler)

    # Activate the server; this will keep running until you
    # interrupt the program with Ctrl-C
    server.serve_forever()
