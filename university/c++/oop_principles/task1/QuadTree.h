#include "NodeType.h"

class QuadTree {

	private:
		unsigned width;
		unsigned height;
		unsigned level;
		unsigned x;
		unsigned y;
		char value = 'N';
		bool expand = false;
		NodeType type = NodeType::NIL;
		QuadTree * children[4]; //0 : nw, 1 : ne, 2 : se, 3 : sw
		
	public:
		QuadTree(unsigned width, unsigned height);
		QuadTree(unsigned width, unsigned height, unsigned level, unsigned x, unsigned y);

		void setWidth(unsigned width);
		unsigned getWidth();

		void setHeight(unsigned height);
		unsigned getHeight();

		void setLevel(unsigned level);
		unsigned getLevel();

		void setX(unsigned x);
		unsigned getX();

		void setY(unsigned y);
		unsigned getY();

		void setValue(char value);
		char getValue();

		void setType(NodeType type);
		NodeType getType();

		void setExpand(bool expand);
		bool isExpandable();

		QuadTree * * getChildren();

		void initializeChildren();
		void print();
};
