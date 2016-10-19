import java.io.*;
import java.util.*;

public class scratchpad {
static int calculate_bid(int player, int pos,int[] first_moves,int[] second_moves) {
	int defBid = 14;
	int remMoney = 100;
	int[] f_moves = new int[first_moves.length];
	int[] s_moves =new int[first_moves.length];
	if(first_moves.length-1 ==0)
	{
		return 16;
	}
	int remDistance = pos;
	if(player==2)
	{
		pos=10-pos;
		System.arraycopy( first_moves, 0, s_moves, 0, first_moves.length );
		System.arraycopy( second_moves, 0, f_moves, 0, first_moves.length );
	}
	else
	{
		System.arraycopy( first_moves, 0, f_moves, 0, first_moves.length );
		System.arraycopy( second_moves, 0,s_moves, 0, first_moves.length );
	}
	
//calculate how much money do we have
	remMoney = calculate_remMoney(f_moves,s_moves);
	
//calculate ideally how much money(max) can we bid on next move
	int maxBid = remMoney/pos;
	
//determine did we win last time, if yes then calculate how many times in a row
	//***winStreak =0 -lost last turn | winStreak =1 -won last turn | winStreak=2 -won twice
	int winStreak = getWins(f_moves,s_moves); 	
	
//if we lost last turn, take revenge
	if(pos==1)
        {
        return calculate_remMoney(f_moves,s_moves);
    }
    else if(winStreak==0)
	{
		return Math.min(maxBid, f_moves[f_moves.length-1]+1);
	}
	else if(winStreak==1)
	{
		return Math.min(maxBid, defBid);
	}
	else if(winStreak==2)
	{
        if(maxBid<=5){   
            return Math.min(f_moves[f_moves.length-1],maxBid);}
        else
        {
            return Math.min(f_moves[f_moves.length-1],maxBid-5);
        }
	}
    else if(winStreak==100)
        {
        return Math.min(maxBid, defBid);
    }
	else
	{
		return Math.min(maxBid, defBid);
	}
	
	//if(winStreak)
	
	//return bid;
    }



static int getWins(int[] f_moves,int[] s_moves)
{
	int wins = 0;
	int fMoveCount = f_moves.length;
	int sMoveCount = s_moves.length;
	if(f_moves[fMoveCount-1]>s_moves[sMoveCount-1])
	{
		if(f_moves[fMoveCount-1]>s_moves[sMoveCount-1] && f_moves[fMoveCount-2]>s_moves[sMoveCount-2])
		{
			wins=2;
		}
		else
		{
			wins=1;
		}
	}
    
    else if(f_moves[fMoveCount-1]==s_moves[sMoveCount-1])
        {
        wins=100;
    }
	else
	{
		wins=0;
	}
	
	return wins;
}
static int calculate_remMoney(int[] f_moves,int[] s_moves)
{
	int rem =0;
	
	boolean tieWin=true;
	int spent=0;


//calculate remaining money
	for(int i=0;i<f_moves.length;i++)
	{
		int tieCount=0;
//----------
		//find out tie count
		for(int j=0;j<=i;j++)
		{
			if(f_moves[j]==s_moves[j])
			{
				tieCount=tieCount+1;
			}
		}
		
	//figure out if its our tie turn
		if(tieCount%2==0)
		{
			tieWin=false;
		}
		else
		{
			tieWin=true;
		}
//-----
		if(f_moves[i]>s_moves[i])
		{
			spent = spent+f_moves[i];
		}
		else if(f_moves[i]==s_moves[i] && tieWin)
		{
			spent = spent+f_moves[i];
		}
		rem=100-spent;
		
		
	}
	return rem;
		
	
	
}


public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int player = in.nextInt();                     //1 if first player 2 if second
        int scotch_pos = in.nextInt();                 //position of the scotch
        int bid = 0;                                   //Amount bid by the player
        in.useDelimiter("\n");
        String first_move = in.next();
        String[] move_1 = first_move.split(" ");
        String second_move = in.next();
        String[] move_2= second_move.split(" ");
        int[]first_moves = new int[move_1.length];
        int[] second_moves = new int[move_2.length];
        if(first_move.equals("") == false) {
            for (int i=0;i<move_1.length;i++) {
                first_moves[i] = Integer.parseInt(move_1[i]);
                second_moves[i] = Integer.parseInt(move_2[i]);
            }
        }
        bid = calculate_bid(player,scotch_pos,first_moves,second_moves);
        System.out.print(bid);
    }
}
