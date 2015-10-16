import java.io.*;

public class Lamport
{
public static void main(String args[])throws Exception
{
BufferedReader ob=new BufferedReader(new InputStreamReader(System.in));
int siteID=0,free=0;
String flag;String check;
Site S1=new Site();
Site S2=new Site();
Site S3=new Site();
RequestQueue R1=new RequestQueue();
RequestQueue R2=new RequestQueue();
RequestQueue R3=new RequestQueue();

S1.ID=1;
S2.ID=2;
S3.ID=3;

R1.ID=1;
R2.ID=2;
R3.ID=3;


do
{
System.out.println();System.out.println();System.out.println();
System.out.print("Enter the Site No. which requires C.S. ACCESS (1-3) : ");
siteID=Integer.parseInt(ob.readLine());

switch(siteID)
{
case 1:
S1.sendRequest("R1",R1);
S2.receiveRequest("R1",R2);
S3.receiveRequest("R1",R3);
S2.sendReply("S2",R2);
S3.sendReply("S3",R3);
S1.receiveReply();
if(free==0)
{enterCS("SITE 1",R1);free=1;}
else
{System.out.println();
System.out.println();
}
System.out.print("Release Critical Section (y/n) ? : ");
flag=ob.readLine();
if(flag.equals("y") || flag.equals("Y"))
    {
    releaseCS(R1,R2,R3);free=0;
    }

break;


case 2:
S2.sendRequest("R2",R2);
S1.receiveRequest("R2",R1);
S3.receiveRequest("R2",R3);
S1.sendReply("S1",R1);
S3.sendReply("S3",R3);
S2.receiveReply();
if(free==0)
{enterCS("SITE 2",R1);free=1;}
else
{System.out.println();
System.out.println();
}
do
{
System.out.print("Release Critical Section (y/n) ? : ");
flag=ob.readLine();
if(flag.equals("y") || flag.equals("Y"))
    {releaseCS(R1,R2,R3);free=0;}
try
{
check=R1.checkStatus();
}
catch(NullPointerException n)
{check="none";
}
}while(!check.equals("none") && (flag.equals("y") || flag.equals("Y")));
break;

case 3:
S3.sendRequest("R3",R3);
S1.receiveRequest("R3",R1);
S2.receiveRequest("R3",R2);
S1.sendReply("S1",R1);
S2.sendReply("S2",R2);
S3.receiveReply();
if(free==0)
{enterCS("SITE 3",R1);free=1;}
else
{System.out.println();
System.out.println();
}
System.out.print("Release Critical Section (y/n) ? : ");
flag=ob.readLine();
if(flag.equals("y") || flag.equals("Y"))
    {releaseCS(R1,R2,R3);free=0;}

break;

default:System.exit(0);
}

}while(siteID!=0);

}

static void enterCS(String s,RequestQueue R1)throws Exception
{try
{
s=R1.checkStatus();
}
catch(NullPointerException n)
{s="none";
}
System.out.println();
System.out.println();
System.out.println("Critical Section");
System.out.println();
System.out.println("   **********");
System.out.println("   *        *");
System.out.println("   * "+s+" *");
System.out.println("   *        *");
System.out.println("   **********");
System.out.println();
System.out.println();
}
static void releaseCS(RequestQueue R1,RequestQueue R2,RequestQueue R3)throws Exception
{
R1.displayRequest(1);
R2.displayRequest(1);
R3.displayRequest(1);
String check;
try
{
check=R1.checkStatus();
}
catch(NullPointerException n)
{check="none";
}
if(check.equals("none"))
{System.out.println();
System.out.println();
System.out.println("Critical Section");
System.out.println();
System.out.println("   **********");
System.out.println("   *        *");
System.out.println("   *        *");
System.out.println("   *        *");
System.out.println("   **********");
System.out.println();
System.out.println();
}
else
{
System.out.println();
System.out.println();
System.out.println("Critical Section");
System.out.println();
System.out.println("   **********");
System.out.println("   *        *");
System.out.println("   * "+check+" *");
System.out.println("   *        *");
System.out.println("   **********");
System.out.println();
System.out.println();
}

}

}

class Site
{
int ID;
int[] counters = new int[3];

int flag;
int i=0;
void sendRequest(String R,RequestQueue RQ)
{
RQ.addRequest(R);
RQ.displayRequest(0);
}

void receiveRequest(String R,RequestQueue RQ)
{
RQ.addRequest(R);
}

void sendReply(String R,RequestQueue RQ)throws Exception
{
for(int i=0 ; i<=5 ; i++)
{System.out.print("\rWaiting for Reply from "+R+".../");
Thread.sleep(200);
System.out.print("\rWaiting for Reply from "+R+"...\\");
Thread.sleep(200);
}
System.out.print("\r                                                  ");
RQ.displayRequest(0);
}

void receiveReply()
{

}

}

class Message
{
int[] timeStamp = new int[3];
String type;
}

class RequestQueue
{int i=0;
int ID;
int k=0;
String[] requestQ = new String[50];
//int x=0,y=0,z=0;

void addRequest(String RS)
{
requestQ[i++]=RS+" ";}

void displayRequest(int flag)
{
if(flag==1)
    k++;
 System.out.println();
System.out.print("Site "+ID+" Request Queue : ");
for(int j=k ; j<i ; j++)
{System.out.print(" "+requestQ[j]);}
System.out.println();
//System.out.println("Counter : ["+ x++ +","+ y++ +","+ z++ +"]" );
//System.out.println();
}

String checkStatus()
{
if(requestQ[k].equals("R1 ")) return "SITE 1";
 else if(requestQ[k].equals("R2 ")) return "SITE 2";
 else if(requestQ[k].equals("R3 ")) return "SITE 3";

else return "none";
}
}
