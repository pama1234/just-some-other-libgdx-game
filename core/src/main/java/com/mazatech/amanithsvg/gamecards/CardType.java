/****************************************************************************
 ** Copyright (c) 2013-2018 Mazatech S.r.l. All rights reserved.
 ** 
 ** Redistribution and use in source and binary forms, with or without modification, are
 * permitted (subject to the limitations in the disclaimer below) provided that the following
 * conditions are met:
 ** 
 ** - Redistributions of source code must retain the above copyright notice, this list of
 * conditions and the following disclaimer.
 ** 
 ** - Redistributions in binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other materials provided
 * with the distribution.
 ** 
 ** - Neither the name of Mazatech S.r.l. nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written
 * permission.
 ** 
 ** NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS LICENSE. THIS
 * SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY
 * AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER
 ** OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 ** OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 ** 
 ** For any information, please contact info@mazatech.com
 ** 
 ****************************************************************************/
package com.mazatech.amanithsvg.gamecards;

public enum CardType{
    Undefined(0),
    BackSide(1),
    Panda(2),
    Monkey(3),
    Orangutan(4),
    Panther(5),
    Puma(6),
    Leopard(7),
    Lion(8),
    Cougar(9),
    Tiger(10),
    Elephant(11),
    Penguin(12),
    Zebra(13),
    Hen(14),
    Rooster(15),
    Pig(16),
    Dog(17),
    Rabbit(18),
    Owl(19),
    Sheep(20),
    Cat(21),
    Deer(22),
    Donkey(23),
    Cow(24),
    Fox(25);
    CardType(int internalEnum) {
        _internalEnum=internalEnum;
    }
    public int getValue() {
        return _internalEnum;
    }
    public static CardType fromValue(int internalEnum) {
        return _allValues[internalEnum];
    }
    // Number of total animal types
    public static int count() {
        return (CardType.Fox.getValue()-CardType.Panda.getValue())+1;
    }
    // Select a random animal
    public static CardType random() {
        int v=(int)(Math.random()*(float)CardType.count())+CardType.Panda.getValue();
        return fromValue(v);
    }
    // Get the next animal in the list
    public CardType next() {
        int current=this.getValue();
        int next=((current+1)%CardType.count())+CardType.Panda.getValue();
        return CardType.fromValue(next);
    }
    // Map an animal name to the respective enum value (e.g. "puma" -> Puma)
    public static CardType fromName(final String name) {
        CardType result;
        String s=name.toLowerCase();
        if(s.contains("panda")) {
            result=Panda;
        }else if(s.contains("monkey")) {
            result=Monkey;
        }else if(s.contains("orangutan")) {
            result=Orangutan;
        }else if(s.contains("panther")) {
            result=Panther;
        }else if(s.contains("puma")) {
            result=Puma;
        }else if(s.contains("leopard")) {
            result=Leopard;
        }else if(s.contains("lion")) {
            result=Lion;
        }else if(s.contains("cougar")) {
            result=Cougar;
        }else if(s.contains("tiger")) {
            result=Tiger;
        }else if(s.contains("elephant")) {
            result=Elephant;
        }else if(s.contains("penguin")) {
            result=Penguin;
        }else if(s.contains("zebra")) {
            result=Zebra;
        }else if(s.contains("hen")) {
            result=Hen;
        }else if(s.contains("rooster")) {
            result=Rooster;
        }else if(s.contains("pig")) {
            result=Pig;
        }else if(s.contains("dog")) {
            result=Dog;
        }else if(s.contains("rabbit")) {
            result=Rabbit;
        }else if(s.contains("owl")) {
            result=Owl;
        }else if(s.contains("sheep")) {
            result=Sheep;
        }else if(s.contains("cat")) {
            result=Cat;
        }else if(s.contains("deer")) {
            result=Deer;
        }else if(s.contains("donkey")) {
            result=Donkey;
        }else if(s.contains("cow")) {
            result=Cow;
        }else if(s.contains("fox")) {
            result=Fox;
        }else if(s.contains("back")) {
            result=BackSide;
        }else {
            result=Undefined;
        }
        return result;
    }
    private final int _internalEnum;
    private static CardType[] _allValues=values();
}
