/******************************************************************************
 *
 *  CLASSE BUTTONTABCOMPONENT
 *  EDITADO POR: Renan Garcia de Oliveira
 *
 *  - Adiciona um botão de fechar em uma aba de um JTabbedPane
 *
 ******************************************************************************/
package View;
import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.*;
public class ButtonTabComponent extends JPanel {
    private final JTabbedPane pane;
      //
     //CONSTRUTOR
    //
    //Início...
    public ButtonTabComponent(final JTabbedPane pane) {
        //unset default FlowLayout' gaps
        super(new FlowLayout(FlowLayout.LEFT, 0, 0));
        if (pane == null) {
            throw new NullPointerException("TabbedPane is null");
        }
        this.pane = pane;
        setOpaque(false);
        //faz a JLabel ler o título do JTabbedPane
        JLabel label = new JLabel() {
            public String getText() {
                int i = pane.indexOfTabComponent(ButtonTabComponent.this);
                if (i != -1) {
                    return pane.getTitleAt(i);
                }
                return null;
            }
        };
        add(label);
        //adiciona mais espaço entre a label e o botão
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
        //tab button
        JButton button = new TabButton();
        add(button);
        //adiciona mais espaço para o topo do componente
        setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
    }//Fim do construtor.
      //
     //CLASSE TABBUTTON
    //
    //Define as características do botão fechar.
    //
    //Início
    private class TabButton extends JButton implements ActionListener {
        public TabButton() {
            int size = 17;
            setPreferredSize(new Dimension(size, size));
            setToolTipText("Fechar esta aba!");
            //Faz o botão ser igual para todas as Laf's
            setUI(new BasicButtonUI());
            //Torna-o transparente
            setContentAreaFilled(false);
            //Não necessidade de estar com focusable
            setFocusable(false);
            setBorder(BorderFactory.createEtchedBorder());
            setBorderPainted(false);
            //Fazendo um efeito de rolagem
            //usamos o mesmo listener para todos os botões
            addMouseListener(buttonMouseListener);
            setRolloverEnabled(true);
            //Fecha a guia apropriada, clicando no botão
            addActionListener(this);
        }
        public void actionPerformed(ActionEvent e) {
            int i = pane.indexOfTabComponent(ButtonTabComponent.this);
            if (i != -1) {
                pane.remove(i);
            }
        }
        //pinta o X
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            //mudança na imagem para botões pressionados
            if (getModel().isPressed()) {
                g2.translate(1, 1);
            }
            g2.setStroke(new BasicStroke(2));
            g2.setColor(Color.BLACK);
            if (getModel().isRollover()) {
                g2.setColor(Color.MAGENTA);
            }
            int delta = 6;
            g2.drawLine(delta, delta, getWidth() - delta - 1, getHeight() - delta - 1);
            g2.drawLine(getWidth() - delta - 1, delta, delta, getHeight() - delta - 1);
            g2.dispose();
        }
    }//Fim da classe TabButton.
      //
     //MOUSELISTENER
    //
    //Define os eventos de entrada e saida do mouse.
    //
    //Início...
    private final static MouseListener buttonMouseListener = new MouseAdapter() {
        public void mouseEntered(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(true);
            }
        }
        public void mouseExited(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(false);
            }
        }
    };//Fim dos Listeners.
}