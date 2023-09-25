package br.edu.iff.jogoforca.dominio.boneco.texto;

import  br.edu.iff.jogoforca.dominio.boneco.Boneco;

public class BonecoTexto implements Boneco
{
	
	//ATRIBUTOS
	private static BonecoTexto soleInstance;
	
	//CONSTRUTOR SOLEINSTANCE

    private BonecoTexto() {
    }

    public static BonecoTexto getSoleInstance() {
        if (soleInstance == null) return new BonecoTexto();
        else return soleInstance;
    }
	
	
	//MÉTODOS
	
	@Override
	public void exibir(Object contexto, int partes)
	{
		 switch(partes){
         case 1:
             System.out.print("cabeça");
             break;
         case 2:
             System.out.print("cabeça, olho esquerdo");
             break;
         case 3:
             System.out.print("cabeça, olho esquerdo, olho direito");
             break;
         case 4:
             System.out.print("cabeça, olho esquerdo, olho direito, nariz");
             break;
         case 5:
             System.out.print("cabeça, olho esquerdo, olho direito, nariz, boca");
             break;
         case 6:
             System.out.print("cabeça, olho esquerdo, olho direito, nariz, boca, tronco");
             break;
         case 7:
             System.out.print("cabeça, olho esquerdo, olho direito, nariz, boca, tronco, braço esquerdo");
             break;
         case 8:
             System.out.print("cabeça, olho esquerdo, olho direito, nariz, boca, tronco, braço esquerdo, braço direito");
             break;
         case 9:
             System.out.print("cabeça, olho esquerdo, olho direito, nariz, boca, tronco, braço esquerdo, braço direito, perna esquerda");
             break;
         case 10:
             System.out.print("cabeça, olho esquerdo, olho direito, nariz, boca, tronco, braço esquerdo, braço direito, perna esquerda, perna direita");
             break;
     }
		
	}

}