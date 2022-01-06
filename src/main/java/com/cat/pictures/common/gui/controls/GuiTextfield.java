package com.cat.pictures.common.gui.controls;

import com.cat.pictures.common.gui.controls.GuiControl;
import com.cat.pictures.common.gui.event.ControlChangedEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ChatAllowedCharacters;
import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.opengl.GL11;

public class GuiTextfield extends GuiControl {

   private static FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
   public String text;
   private boolean focused = false;
   private int cursorPosition;
   private int scrollOffset = 0;
   private int selEnd = 0;
   private char[] allowedChars;
   private int cursorCounter;


   public GuiTextfield(String name, String text, int x, int y, int width, int height) {
      super(name, x, y, width, height);
      this.text = text;
   }

   public void onLoseFocus() {
      this.focused = false;
   }

   public GuiTextfield setFloatOnly() {
      this.allowedChars = new char[]{'-', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.'};
      return this;
   }

   private String getSelectedText() {
      return this.selEnd != -1 && this.selEnd != this.cursorPosition && this.text != null?this.text.substring(Math.min(this.cursorPosition, this.selEnd), Math.max(this.cursorPosition, this.selEnd)):"";
   }

   public boolean mousePressed(int posX, int posY, int button) {
      boolean wasFocused = this.focused;
      this.focused = true;
      if(!wasFocused) {
         this.cursorCounter = 0;
      }

      int l = posX - (this.posX - this.width / 2);
      String s = fontRenderer.trimStringToWidth(this.text.substring(this.scrollOffset), this.getWidth());
      this.setCursorPosition(fontRenderer.trimStringToWidth(s, l).length() + this.scrollOffset);
      return true;
   }

   private void writeText(String text) {
      String s1 = "";
      String s2 = ChatAllowedCharacters.filerAllowedCharacters(text);
      StringBuilder s3 = new StringBuilder();
      char[] chars = s2.toCharArray();
      int j;
      int k;
      if(this.allowedChars != null) {
         char[] i = chars;
         j = chars.length;

         for(k = 0; k < j; ++k) {
            char l = i[k];
            if(ArrayUtils.contains(this.allowedChars, l)) {
               s3.append(l);
            }
         }

         s2 = s3.toString();
      }

      int var10 = Math.min(this.cursorPosition, this.selEnd);
      j = Math.max(this.cursorPosition, this.selEnd);
      k = 44 - this.text.length() - (var10 - this.selEnd);
      if(this.text.length() > 0) {
         s1 = s1 + this.text.substring(0, var10);
      }

      int var11;
      if(k < s2.length()) {
         s1 = s1 + s2.substring(0, k);
         var11 = k;
      } else {
         s1 = s1 + s2;
         var11 = s2.length();
      }

      if(this.text.length() > 0 && j < this.text.length()) {
         s1 = s1 + this.text.substring(j);
      }

      this.text = s1;
      this.moveCursorBy(var10 - this.selEnd + var11);
      this.raiseEvent(new ControlChangedEvent(this));
   }

   private void moveCursorBy(int offset) {
      this.setCursorPosition(this.selEnd + offset);
   }

   private int getNthWordFromCursor(int pos) {
      return this.getNthWordFromPos(pos);
   }

   private int getNthWordFromPos(int pos) {
      return this.func_146197_a(pos, this.cursorPosition);
   }

   private int func_146197_a(int p_146197_1_, int p_146197_2_) {
      int k = p_146197_2_;
      boolean flag1 = p_146197_1_ < 0;
      int l = Math.abs(p_146197_1_);

      for(int i1 = 0; i1 < l; ++i1) {
         if(flag1) {
            while(k > 0 && this.text.charAt(k - 1) == 32) {
               --k;
            }

            while(k > 0 && this.text.charAt(k - 1) != 32) {
               --k;
            }
         } else {
            int j1 = this.text.length();
            k = this.text.indexOf(32, k);
            if(k == -1) {
               k = j1;
            } else {
               while(k < j1 && this.text.charAt(k) == 32) {
                  ++k;
               }
            }
         }
      }

      return k;
   }

   private void deleteWords(int pos) {
      if(this.text.length() != 0) {
         if(this.selEnd != this.cursorPosition) {
            this.writeText("");
         } else {
            this.deleteFromCursor(this.getNthWordFromCursor(pos) - this.cursorPosition);
         }
      }

      this.raiseEvent(new ControlChangedEvent(this));
   }

   private void deleteFromCursor(int pos) {
      if(this.text.length() != 0) {
         if(this.selEnd != this.cursorPosition) {
            this.writeText("");
         } else {
            boolean flag = pos < 0;
            int j = flag?this.cursorPosition + pos:this.cursorPosition;
            int k = flag?this.cursorPosition:this.cursorPosition + pos;
            String s = "";
            if(j >= 0) {
               s = this.text.substring(0, j);
            }

            if(k < this.text.length()) {
               s = s + this.text.substring(k);
            }

            this.text = s;
            if(flag) {
               this.moveCursorBy(pos);
            }
         }
      }

      this.raiseEvent(new ControlChangedEvent(this));
   }

   private int getWidth() {
      return this.width - 8;
   }

   private void setSelectionPos(int p_146199_1_) {
      int j = this.text.length();
      if(p_146199_1_ > j) {
         p_146199_1_ = j;
      }

      if(p_146199_1_ < 0) {
         p_146199_1_ = 0;
      }

      this.selEnd = p_146199_1_;
      if(fontRenderer != null) {
         if(this.scrollOffset > j) {
            this.scrollOffset = j;
         }

         int k = this.getWidth();
         String s = fontRenderer.trimStringToWidth(this.text.substring(this.scrollOffset), k);
         int l = s.length() + this.scrollOffset;
         if(p_146199_1_ == this.scrollOffset) {
            this.scrollOffset -= fontRenderer.trimStringToWidth(this.text, k, true).length();
         }

         if(p_146199_1_ > l) {
            this.scrollOffset += p_146199_1_ - l;
         } else if(p_146199_1_ <= this.scrollOffset) {
            this.scrollOffset -= this.scrollOffset - p_146199_1_;
         }

         if(this.scrollOffset < 0) {
            this.scrollOffset = 0;
         }

         if(this.scrollOffset > j) {
            this.scrollOffset = j;
         }
      }

   }

   private void setCursorPosition(int pos) {
      this.cursorPosition = pos;
      int j = this.text.length();
      if(this.cursorPosition < 0) {
         this.cursorPosition = 0;
      }

      if(this.cursorPosition > j) {
         this.cursorPosition = j;
      }

      this.setSelectionPos(this.cursorPosition);
   }

   public boolean onKeyPressed(char character, int key) {
      if(!this.focused) {
         return false;
      } else {
         switch(character) {
         case 1:
            this.setCursorPosition(this.text.length());
            this.setSelectionPos(0);
            return true;
         case 3:
            GuiScreen.setClipboardString(this.getSelectedText());
            return true;
         case 22:
            this.writeText(GuiScreen.getClipboardString());
            return true;
         case 24:
            GuiScreen.setClipboardString(this.getSelectedText());
            this.writeText("");
            return true;
         default:
            switch(key) {
            case 14:
               if(GuiScreen.isCtrlKeyDown()) {
                  this.deleteWords(-1);
               } else {
                  this.deleteFromCursor(-1);
               }

               return true;
            case 199:
               if(GuiScreen.isShiftKeyDown()) {
                  this.setSelectionPos(0);
               } else {
                  this.setCursorPosition(0);
               }

               return true;
            case 203:
               if(GuiScreen.isShiftKeyDown()) {
                  if(GuiScreen.isCtrlKeyDown()) {
                     this.setSelectionPos(this.getNthWordFromPos(-1));
                  } else {
                     this.setSelectionPos(this.selEnd - 1);
                  }
               } else if(GuiScreen.isCtrlKeyDown()) {
                  this.setCursorPosition(this.getNthWordFromCursor(-1));
               } else {
                  this.moveCursorBy(-1);
               }

               return true;
            case 205:
               if(GuiScreen.isShiftKeyDown()) {
                  if(GuiScreen.isCtrlKeyDown()) {
                     this.setSelectionPos(this.getNthWordFromPos(1));
                  } else {
                     this.setSelectionPos(this.selEnd + 1);
                  }
               } else if(GuiScreen.isCtrlKeyDown()) {
                  this.setCursorPosition(this.getNthWordFromCursor(1));
               } else {
                  this.moveCursorBy(1);
               }

               return true;
            case 207:
               if(GuiScreen.isShiftKeyDown()) {
                  this.setSelectionPos(this.text.length());
               } else {
                  this.setCursorPosition(this.text.length());
               }

               return true;
            case 211:
               if(GuiScreen.isCtrlKeyDown()) {
                  this.deleteWords(1);
               } else {
                  this.deleteFromCursor(1);
               }

               return true;
            default:
               if(ChatAllowedCharacters.isAllowedCharacter(character)) {
                  this.writeText(Character.toString(character));
                  return true;
               } else {
                  return false;
               }
            }
         }
      }
   }

   public void drawControl(FontRenderer renderer) {
      Gui.drawRect(-1, -1, this.width + 1, this.height + 1, -12895429);
      Gui.drawRect(0, 0, this.width, this.height, 1711276032);
      int enabledColor = 14737632;
      int j = this.cursorPosition - this.scrollOffset;
      int k = this.selEnd - this.scrollOffset;
      String s = fontRenderer.trimStringToWidth(this.text.substring(this.scrollOffset), this.getWidth());
      boolean flag = j >= 0 && j <= s.length();
      boolean flag1 = this.focused && this.cursorCounter / 6 % 2 == 0 && flag;
      boolean l = true;
      int i1 = (this.height - 8) / 2;
      int j1 = 4;
      if(k > s.length()) {
         k = s.length();
      }

      if(s.length() > 0) {
         String flag2 = flag?s.substring(0, j):s;
         j1 = fontRenderer.drawStringWithShadow(flag2, 4, i1, 14737632);
      }

      boolean var14 = this.cursorPosition < this.text.length() || this.text.length() >= 44;
      int k1 = j1;
      if(!flag) {
         k1 = j > 0?4 + this.width:4;
      } else if(var14) {
         k1 = j1 - 1;
         --j1;
      }

      if(s.length() > 0 && flag && j < s.length()) {
         fontRenderer.drawStringWithShadow(s.substring(j), j1, i1, 14737632);
      }

      if(flag1) {
         if(var14) {
            Gui.drawRect(k1, i1 - 1, k1 + 1, i1 + 1 + fontRenderer.FONT_HEIGHT, -3092272);
         } else {
            fontRenderer.drawStringWithShadow("_", k1, i1, 14737632);
         }
      }

      if(k != j) {
         int l1 = 4 + fontRenderer.getStringWidth(s.substring(0, k));
         this.drawCursorVertical(k1, i1 - 1, l1 - 1, i1 + 1 + fontRenderer.FONT_HEIGHT);
      }

   }

   private void drawCursorVertical(int p_146188_1_, int p_146188_2_, int p_146188_3_, int p_146188_4_) {
      int i1;
      if(p_146188_1_ < p_146188_3_) {
         i1 = p_146188_1_;
         p_146188_1_ = p_146188_3_;
         p_146188_3_ = i1;
      }

      if(p_146188_2_ < p_146188_4_) {
         i1 = p_146188_2_;
         p_146188_2_ = p_146188_4_;
         p_146188_4_ = i1;
      }

      if(p_146188_3_ > this.posX + this.width) {
         p_146188_3_ = this.posX + this.width;
      }

      if(p_146188_1_ > this.posX + this.width) {
         p_146188_1_ = this.posX + this.width;
      }

      Tessellator tessellator = Tessellator.instance;
      GL11.glColor4f(0.0F, 0.0F, 255.0F, 255.0F);
      GL11.glDisable(3553);
      GL11.glEnable(3058);
      GL11.glLogicOp(5387);
      tessellator.startDrawingQuads();
      tessellator.addVertex((double)p_146188_1_, (double)p_146188_4_, 0.0D);
      tessellator.addVertex((double)p_146188_3_, (double)p_146188_4_, 0.0D);
      tessellator.addVertex((double)p_146188_3_, (double)p_146188_2_, 0.0D);
      tessellator.addVertex((double)p_146188_1_, (double)p_146188_2_, 0.0D);
      tessellator.draw();
      GL11.glDisable(3058);
      GL11.glEnable(3553);
   }

}
