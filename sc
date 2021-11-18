using System;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading;

namespace Server
{
    class Program
    {
        static Socket sck;//hold
        static Socket acc;//accept
        static int port = 9000;
        static IPAddress ip;
        static Thread rec;
        static string name;
        
        static string GetIp()
        {
            string hostname = Dns.GetHostName();
            IPHostEntry ipEntry = Dns.GetHostEntry(hostname);
            IPAddress[] addr = ipEntry.AddressList;
            return addr[addr.Length - 1].ToString();
        }
        static void recV() 
        {
            while (true)
            {
               // Thread.Sleep(500);
                byte[] buffer = new byte[255];
                int rec = acc.Receive(buffer,0,buffer.Length,0);
                Array.Resize(ref buffer,rec);
                Console.WriteLine(Encoding.Default.GetString(buffer));
            }
        }

        static void Main(string[] args)
        {
            Console.Title = "SERVER";
            rec = new Thread(recV);
            Console.WriteLine("Your local Ip: " + GetIp());
            //  Console.WriteLine("plz ent yor name");
            name = "sERCER";
            //Console.WriteLine("plz ent your host port");
            //string inputport = Console.ReadLine();
            //try
            //{
            //    port = Convert.ToInt32(inputport);
            //}
            //catch
            //{
            //    port = 9000;
            //}
            ip = IPAddress.Parse(GetIp());
            sck = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
            sck.Bind(new IPEndPoint(ip, 1000));
            sck.Listen(0);
            acc = sck.Accept();
            rec.Start();
            while (true)
            {
                byte[] sdata = Encoding.Default.GetBytes("<"+name+">"+Console.ReadLine());
                acc.Send(sdata, 0, sdata.Length, 0);
            }
        }
    }
}

///

using System;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading;

namespace Client
{
    class Program
    {
        static string name;
        static int port = 9000;
        static IPAddress ip;
        static Thread rec;
        static Socket sck;

        static void Main(string[] args)
        {
            Console.Title = "CLIENT-2";

            rec = new Thread(recV);
            //  Console.WriteLine("plz ent yor name");
            name = "CLIENT-2";
          //  Console.WriteLine("plz entr the ip of the servre");
           // ip = IPAddress.Parse(Console.ReadLine());
           // Console.WriteLine("plz ent the port");
          //  string inputport = Console.ReadLine();
            //try
            //{
            //    port = 1000;
            //    //port = Convert.ToInt32(inputport);
            //}
            //catch
            //{
            //    port = 9000;
            //}
            sck = new Socket(AddressFamily.InterNetwork,SocketType.Stream,ProtocolType.Tcp);
            //sck.Connect(new IPEndPoint(ip,port));
            sck.Connect(new IPEndPoint(IPAddress.Parse("192.168.3.109"),1000));
            rec.Start();

            byte[] conmsg = Encoding.Default.GetBytes("<" +name+ ">"+ "connected");
            sck.Send(conmsg,0,conmsg.Length,0);

            while (sck.Connected)
            {
                byte[] sdata = Encoding.Default.GetBytes("<"+name+">"+Console.ReadLine());
                sck.Send(sdata,0,sdata.Length,0);
            }

        }
        static void recV()
        {
            while (true)
            {
                Thread.Sleep(500);
                byte[] buffer = new byte[255];
                int rec = sck.Receive(buffer, 0, buffer.Length, 0);
                Array.Resize(ref buffer, rec);
                Console.WriteLine(Encoding.Default.GetString(buffer));
            }
        }
    }
}

