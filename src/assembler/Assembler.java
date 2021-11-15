/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assembler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author KHALID
 */
public class Assembler {

    /**
     * @param args the command line arguments
     */
 
    public static void main(String[] args) throws FileNotFoundException, IOException {
         
        int lc = 0 ;
        int error=0;
        String number ="";
        int notfoundinmri=0;
        String mr_inst,nmr_inst;
          File file = new File("C:\\Users\\khali\\Documents\\NetBeansProjects\\Assembler\\input4.txt"); 
          File file2 = new File("C:\\Users\\khali\\Documents\\NetBeansProjects\\Assembler\\symboladd.txt"); 
          File file3 = new File("C:\\Users\\khali\\Documents\\NetBeansProjects\\Assembler\\output.txt"); 
          File file4 = new File("C:\\Users\\khali\\Documents\\NetBeansProjects\\Assembler\\MRI.txt"); 
          File file5 = new File("C:\\Users\\khali\\Documents\\NetBeansProjects\\Assembler\\NMRI.txt"); 
        
          BufferedWriter writer = new BufferedWriter(new FileWriter(file2));
          BufferedWriter outtwriter = new BufferedWriter(new FileWriter(file3));
          BufferedReader br = new BufferedReader(new FileReader(file)); 
          BufferedReader MRIREAD = new BufferedReader(new FileReader(file4)); 
          BufferedReader NMRIREAD = new BufferedReader(new FileReader(file5)); 
          
          String st; 
               while ((st = br.readLine()) != null) 
              {
              if(st.charAt(0) == 32 && st.charAt(1) == 32 && st.charAt(2) == 32 && st.charAt(3) == 32) // not label
              {
               if(st.charAt(4) == 79 && st.charAt(5) == 82 && st.charAt(6) == 71) // check if org
               {
                 for (char letter : st.toCharArray()) {
				if (letter >= '0' && letter <= '9')
					number += letter;
			};
                        lc = Integer.parseInt(number);
               }
               else // not org
               {
                   if(st.charAt(4) == 69 && st.charAt(5) == 78 && st.charAt(6) == 68) // if END
                   {
                       br.close();
                       break;
                   }
                   else // not end
                   {
                   lc = lc + 1;
                   }
               
               }
               
              }
              else
              {
                  if(Character.isDigit(st.charAt(0)))  
                  {
                  System.out.println("Label should start with letter at line " + lc);
                  error++;
                  }
                  if(!(st.charAt(3) == 44)) 
                  {
                  System.out.println("Missing comma after label at line " + lc);
                  error++;
                  }
                  
                  
                  if(error == 0)
                  {
                  if(st.charAt(1) == 44)
                  {
                   writer.write(st.charAt(0));
                   writer.write(" ");
                   writer.write(" ");
                  }
                  else if(st.charAt(2) == 44)
                  {
                   writer.write(st.charAt(0));
                   writer.write(st.charAt(1));
                   writer.write(" ");
                  }
                  else if(st.charAt(3) == 44)
                  {
                   writer.write(st.charAt(0));
                   writer.write(st.charAt(1));
                   writer.write(st.charAt(2));
                  }
                  
                 
                    writer.newLine();
                    writer.write(""+lc);    
                    lc = lc + 1;
                    writer.newLine();
                  }
    
    
              }  
               
               }
               
            writer.close();
                       br = new BufferedReader(new FileReader(file)); 
                       lc =  0;
                while ((st = br.readLine()) != null) 
              {
              if(st.charAt(4) == 79)
              { // IF ORG
              if(st.charAt(5) == 82)
              {
              if(st.charAt(6) == 71)
              {
                   for (char letter : st.toCharArray()) {
				if (letter >= '0' && letter <= '9')
					number += letter;
			};
                        lc = Integer.parseInt(number);
              }
               else
                  System.out.println("you typed ORG wrongly the third letter should be G");
              }
               else
                  System.out.println("you typed ORG wrongly the second letter should be R");
              }
             
                else if(st.charAt(4) == 69)// if END
                {
                if(st.charAt(5) == 78)     
                {
                    if(st.charAt(6) == 68) 
                    {
                    br.close();
                         break;
                    } 
                     else
                  System.out.println("you typed END wrongly the third letter should be D");
                    }
                 else
                  System.out.println("you typed END wrongly the second letter should be N");
                }
                
                else if(st.charAt(4) == 68 && st.charAt(5) == 69 && st.charAt(6) == 67) // if dec
                {
                   String newletter = st.substring(8);
             
                        int decimalnumber = Integer.parseInt(newletter);  

                        outtwriter.write(Integer.toBinaryString(decimalnumber));
                                           outtwriter.newLine();

                 }
                else  if(st.charAt(4) == 72 && st.charAt(5) == 69 && st.charAt(6) == 88 ) // if hex
                {
                         String newletter = st.substring(8);
                
                        int hexnumber = Integer.parseInt(newletter, 16);
                        outtwriter.write(Integer.toBinaryString(hexnumber));
                                           outtwriter.newLine();
                        
                }
                else
                {
                 MRIREAD = new BufferedReader(new FileReader(file4)); 
                  while ((mr_inst = MRIREAD.readLine()) != null) 
                   
                  {
                       
                      if(mr_inst.charAt(0) == st.charAt(4)){
                          if(mr_inst.charAt(1) == st.charAt(5)){
                              if(mr_inst.charAt(2) == st.charAt(6)) 
                           {//found MRI
                               
                               if(st.charAt(11) == 49)
                               {
                                outtwriter.write("1");                               
                               }
                               else
                               {
                                outtwriter.write("0");                                                              
                               }
                               outtwriter.write(mr_inst.substring(4));
                               outtwriter.newLine();
                               
                           }     
                      }
                }
                  
                  }
                  MRIREAD.close();
                                   NMRIREAD = new BufferedReader(new FileReader(file5)); 

                  while ((nmr_inst = NMRIREAD.readLine()) != null) 
                  {
                   if(nmr_inst.charAt(0) == st.charAt(4)){
                   if(nmr_inst.charAt(1) == st.charAt(5))
                   {
                   if(nmr_inst.charAt(2) == st.charAt(6))
                   {
                   outtwriter.write(nmr_inst.substring(4));
                   outtwriter.newLine();
                   }
                   }
                  
                   }
                     
                  
                  }
                  NMRIREAD.close();
                  
                }
               }
               
            outtwriter.close();
          
                
            } 
               
    }
    

