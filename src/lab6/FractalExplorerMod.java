package lab6;

import java.awt.geom.Rectangle2D.Double;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;


/** В данном классе добавлена многопоточная реализация */

public class FractalExplorerMod {
    private int size;
    private JImageDisplay image;
    private FractalGenerator fcGen;
    private Rectangle2D.Double range;
    private JComboBox<Object> box;
    private JButton btnReset;
    private JButton btnSave;
    private JFrame frame;
    private int rowsremaining;

    public FractalExplorerMod(int size) {
        this.size = size;
        this.fcGen = new FractalGenerator() {
            @Override
            public void getInitialRange(Double range) {

            }

            @Override
            public int numIterations(double x, double y) {
                return 0;
            }
        };
        this.range = new Rectangle2D.Double();
        fcGen.getInitialRange(this.range);
        createAndShowGUI();

    }

    public void createAndShowGUI() {
        frame = new JFrame("Fractals Explorer");
        image = new JImageDisplay(size, size);
        btnReset = new JButton("Reset");
        btnSave = new JButton("Save image");
        //btnReset.setActionCommand("Reset");
        //btnSave.setActionCommand("Save image");
        JLabel label = new JLabel("Fractal: ");// добавление подписи
        box = new JComboBox<>();// сoздание выпадающего списка
        //box.setActionCommand("box");
        box.addItem(new Mandelbrot());
        box.addItem(new Tricorn());
        box.addItem(new BurningShip());

        JPanel panelBox = new JPanel();
        panelBox.add(label);
        panelBox.add(box);

        JPanel panelBtn = new JPanel();
        panelBtn.add(btnReset);
        panelBtn.add(btnSave);

        ActionHandler handler = new ActionHandler();
        btnReset.addActionListener(handler);
        btnSave.addActionListener(handler);
        box.addActionListener(handler);
        image.addMouseListener(new MouseHandler());

        frame.add(image, BorderLayout.CENTER);
        frame.add(panelBtn, BorderLayout.SOUTH);
        frame.add(panelBox, BorderLayout.NORTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        drawFractal();
    }
    /**функция должна вызвать метод enableUI (false), чтобы отключить все элементы пользовательского интерфейса во время рисования,
     *она должна установить значение «rows remaining» равным общему количеству строк, которые нужно нарисовать.*/
    private void drawFractal() {
        enableUI(FALSE);
        rowsremaining = size;
        for (int y = 0; y < size; y++) {
            new FractalWorker(y).execute();
        }
    }
    /**включает или отключает кнопки с выпадающим списком в пользовательском интерфейсе
     * на основе указанного параметра*/
    public void enableUI(boolean val) {
        if (val == TRUE) {
            frame.setEnabled(TRUE);
        } else {
            frame.setEnabled(FALSE);
        }
    }

    public class ActionHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btnReset) {
                fcGen.getInitialRange(range);
                drawFractal();
            } else if (e.getSource() == btnSave) {
                JFileChooser chooser = new JFileChooser();// файл изображения в png
                FileFilter filter = new FileNameExtensionFilter("PNG Images", "PNG");
                chooser.setFileFilter(filter);
                chooser.setAcceptAllFileFilterUsed(false);
                if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    try { // агрузка и сохранение изображения
                        ImageIO.write(image.getBufferedImage(), "png", new File(chooser.getSelectedFile() + ".PNG"));
                    } catch (IOException ex) { // информация об ошибке
                        System.out.println("Failed to save image!");
                    }
                } else {
                    System.out.println("No file chosen!");
                }
            } else if (e.getSource() == box) {
                fcGen = (FractalGenerator) box.getSelectedItem();
                fcGen.getInitialRange(range);
                drawFractal();
            }
        }
    }

    public class MouseHandler implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            double mouseX = FractalGenerator.getCoord(range.x, range.x + range.width, size, e.getX());
            double mouseY = FractalGenerator.getCoord(range.y, range.y + range.width, size, e.getY());
            System.out.println(mouseX + " " + mouseY);
            if (e.getButton() == MouseEvent.BUTTON1) {
                fcGen.recenterAndZoomRange(range, mouseX, mouseY, 0.5);
                drawFractal();
            } else if (e.getButton() == MouseEvent.BUTTON3) {
                fcGen.recenterAndZoomRange(range, mouseX, mouseY, 2);
                drawFractal();
            }
        }

        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }
    }
    /**Отвечает за вычисление значений цвета для одной строки фрактала*/
    private class FractalWorker extends SwingWorker<Object, Object> {
        int y;
        int[] array;

        FractalWorker(int y) {
            this.y = y;
        }
        /**метод, который фактически выполняет фоновые операции.
         * Swing вызывает этот метод в фоновом потоке, а не в потоке обработке событий. */
        @Override
        protected Object doInBackground() throws Exception {
            array = new int[size];
            for (int x = 0; x < size; x++) {
                double xCoord = FractalGenerator.getCoord(range.x, range.x + range.width, size, x);
                double yCoord = FractalGenerator.getCoord(range.y, range.y + range.width, size, y);
                double numIters = fcGen.numIterations(xCoord, yCoord);
                if (numIters == -1) array[x] = 0;
                else {
                    float hue = 0.7f + (float) numIters / 200f;
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                    array[x] = rgbColor;
                }
            }
            return null;
        }
        /**Метод вызывается, когда фоновая задача завершена*/
        protected void done() {
            for (int x = 0; x < size; x++) {
                image.drawPixel(x, y, array[x]);
            }
            image.repaint(0, 0, y, size, 1);
            rowsremaining--; // завершена ли прорисовка?
            if (rowsremaining == 0) {
                enableUI(TRUE);
            }
            super.done();
        }
    }

    public static void main(String[] args) {
        new FractalExplorerMod(700);
    }

}